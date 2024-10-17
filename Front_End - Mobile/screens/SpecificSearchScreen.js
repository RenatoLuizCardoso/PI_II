import React, { useState, useEffect } from "react";
import RNPickerSelect from "react-native-picker-select";
import axios from "axios";
import {
  View,
  Image,
  StyleSheet,
  Text,
  TouchableOpacity,
  FlatList,
  ScrollView,
} from "react-native";
import { useFonts } from "expo-font";

const API_URL = "http://192.168.11.174:8080";

export default function SpecificSearchScreen({ navigation }) {
  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

  const [errorMessage, setErrorMessage] = useState(null);
  const [timetable, setTimetable] = useState([]);
  const [teachers, setTeachers] = useState([]);
  const [courses, setCourses] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [selectedTeacher, setSelectedTeacher] = useState(null);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [selectedSubject, setSelectedSubject] = useState(null);
  const [currentDayIndex, setCurrentDayIndex] = useState(0);

  const daysOfWeek = ["Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"];

  useEffect(() => {
    fetchFilters();
    fetchData(); // Fetch all data initially
  }, []);

  const fetchData = async () => {
    try {
      const timetableResponse = await axios.get(`${API_URL}/schedule/`);
      setTimetable(timetableResponse.data);
    } catch (error) {
      console.error(error);
      setErrorMessage("Erro ao carregar os dados. Tente novamente mais tarde.");
    }
  };

  const fetchFilters = async () => {
    try {
      const teachersResponse = await axios.get(`${API_URL}/teacher/`);
      const subjectsResponse = await axios.get(`${API_URL}/subject/`);
      const coursesResponse = await axios.get(`${API_URL}/course/`);

      const formattedTeachers = teachersResponse.data.map((teacher) => ({
        label: teacher.teacherName,
        value: teacher.teacherName,
      }));

      const formattedCourses = coursesResponse.data.map((course) => ({
        label: course.courseName,
        value: course.courseName,
      }));

      const formattedSubjects = subjectsResponse.data.map((subject) => ({
        label: subject.subjectName,
        value: subject.subjectName,
      }));

      setTeachers(formattedTeachers);
      setCourses(formattedCourses);
      setSubjects(formattedSubjects);
    } catch (error) {
      console.error("Erro ao carregar os filtros", error);
    }
  };

  const filterTimetable = () => {
    return timetable.filter((item) => {
      const matchesTeacher = selectedTeacher ? item.teacher === selectedTeacher : true;
      const matchesCourse = selectedCourse ? item.course === selectedCourse : true;
      const matchesSubject = selectedSubject ? item.subject === selectedSubject : true;
      return matchesTeacher && matchesCourse && matchesSubject;
    });
  };

  const handleDayChange = (direction) => {
    if (direction === "prev") {
      setCurrentDayIndex((prevIndex) =>
        prevIndex === 0 ? daysOfWeek.length - 1 : prevIndex - 1
      );
    } else {
      setCurrentDayIndex((prevIndex) =>
        prevIndex === daysOfWeek.length - 1 ? 0 : prevIndex + 1
      );
    }
  };

  const getDayTimetable = (day) => {
    return filterTimetable().filter((item) => item.time.includes(day));
  };

  const renderTimetableItem = ({ item }) => (
    <View style={styles.tableRow}>
      <Text style={styles.tableCell}>{item.course}</Text>
      <Text style={styles.tableCell}>{item.semester}</Text>
      <Text style={styles.tableCell}>{item.teacher}</Text>
      <Text style={styles.tableCell}>{item.subject}</Text>
      <Text style={styles.tableCell}>{item.time}</Text>
    </View>
  );

  if (!fontsLoaded) {
    return null;
  }

  const currentDay = daysOfWeek[currentDayIndex];
  const dayTimetable = getDayTimetable(currentDay);

  return (
    
    <ScrollView style={styles.container}>
      <Image source={require("../assets/fatec-logo.png")} style={styles.logo} />
      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate("HomeScreen")}>
        <Text style={styles.voltar}>Voltar</Text>
      </TouchableOpacity>
      <Text style={styles.title}>Filtro Específico</Text>

      <RNPickerSelect
        onValueChange={(value) => {
          setSelectedTeacher(value);
        }}
        items={teachers}
        placeholder={{ label: "Selecione um Professor", value: null }}
        value={selectedTeacher}
        style={pickerSelectStyles}
      />

      <RNPickerSelect
        onValueChange={(value) => {
          setSelectedCourse(value);
        }}
        items={courses}
        placeholder={{ label: "Selecione um curso", value: null }}
        value={selectedCourse}
        style={pickerSelectStyles}
      />

      <RNPickerSelect
        onValueChange={(value) => {
          setSelectedSubject(value);
        }}
        items={subjects}
        placeholder={{ label: "Selecione uma Disciplina", value: null }}
        value={selectedSubject}
        style={pickerSelectStyles}
      />

      <View style={styles.table}>
        <View style={styles.tableHeader}>
          <Text style={styles.tableHeaderText}>Curso</Text>
          <Text style={styles.tableHeaderText}>Semestre</Text>
          <Text style={styles.tableHeaderText}>Professor</Text>
          <Text style={styles.tableHeaderText}>Disciplina</Text>
          <Text style={styles.tableHeaderText}>Horário</Text>
        </View>
        {dayTimetable.length > 0 ? (
          <FlatList
            data={dayTimetable}
            renderItem={renderTimetableItem}
            keyExtractor={(item) => item.scheduleId.toString()}
          />
        ) : (
          <Text style={styles.emptyMessage}>Nenhum horário encontrado para {currentDay}</Text>
        )}
      </View>

      <View style={styles.navigation}>
        <TouchableOpacity onPress={() => handleDayChange("prev")}>
          <Text style={styles.navigationText}>{"<"}</Text>
        </TouchableOpacity>
        <Text style={styles.navigationText}>{currentDay}</Text>
        <TouchableOpacity onPress={() => handleDayChange("next")}>
          <Text style={styles.navigationText}>{">"}</Text>
        </TouchableOpacity>
      </View>
      <View style={styles.navbar}>
        <TouchableOpacity
          style={styles.navItem}
          onPress={() => navigation.navigate("HomeScreen")}
        >
          <Text style={styles.navText}>Home</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: "#fff",
  },
  logo: {
    width: 100,
    height: 50,
    alignSelf: "center",
    marginVertical: 20,
    marginTop: 50,
  },
  title: {
    fontSize: 24,
    fontFamily: "Roboto-Medium",
    textAlign: "center",
    marginBottom: 20,
  },
  table: {
    marginBottom: 20,
  },
  voltar:{   
    color: "#f1f1f1",
  },
  tableHeader: {
    flexDirection: "row",
    backgroundColor: "#f1f1f1",
    padding: 10,
  },
  tableHeaderText: {
    flex: 1,
    fontWeight: "bold",
  },
  tableRow: {
    flexDirection: "row",
    padding: 10,
  },
  tableCell: {
    flex: 1,
  },
  emptyMessage: {
    textAlign: "center",
    marginTop: 20,
  },
  button: {
    width: "100%",
    height: 40,
    backgroundColor: "#B20000",
    justifyContent: "center",
   
    alignItems: "center",
    borderRadius: 5,
    width: 100, 
    margintop: 50,
  },
  navigation: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  navbar: {
    flexDirection: "row",
    justifyContent: "space-around",
    alignItems: "center",
    backgroundColor: "#B20000",
    width: "100%",
    position: "absolute",
    bottom: 0,
    height: 60,
  },
  navItem: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  navText: {
    color: "#fff",
    fontFamily: "Roboto-Regular",
    fontSize: 16,
  },
  navigationText: {
    fontSize: 20,
  },
});

const pickerSelectStyles = StyleSheet.create({
  inputIOS: {
    fontSize: 16,
    paddingVertical: 12,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderColor: "gray",
    borderRadius: 4,
    color: "black",
    paddingRight: 30,
    marginBottom: 10,
  },
  inputAndroid: {
    fontSize: 16,
    paddingVertical: 8,
    paddingHorizontal: 10,
    borderWidth: 0.5,
    borderColor: "gray",
    borderRadius: 8,
    color: "black",
    paddingRight: 30,
    marginBottom: 10,
  },
});
