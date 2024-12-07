import React, { useState, useEffect } from "react";
import RNPickerSelect from "react-native-picker-select";
import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { StyleSheet, View, Image, Text, TouchableOpacity, FlatList, ScrollView, Pressable, } from "react-native";
import { useFonts } from "expo-font";
import { Calendar, LocaleConfig } from "react-native-calendars";
import { format, parseISO } from "date-fns";
import { ptBR } from "date-fns/locale";


const API_URL = "https://projeto-integrador-1v4i.onrender.com";

export default function SpecificSearchScreen({ navigation }) {
  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

  const [errorMessage, setErrorMessage] = useState(null);
  const [timetable, setTimetable] = useState([]); // Para armazenar o horário que será exibido
  const [teachers, setTeachers] = useState([]);
  const [courses, setCourses] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [selectedTeacher, setSelectedTeacher] = useState(null);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [selectedSubject, setSelectedSubject] = useState(null);
  const [accessToken, setAccessToken] = useState(null);
  const [selectedDate, setSelectedDate] = useState(null);
  const [formattedDate, setFormattedDate] = useState(null);
  const [weekDay, setWeekDay] = useState(null);
  const [reservations, setReservations] = useState([]); // Dados de reservas
  const [isTokenLoaded, setIsTokenLoaded] = useState(false); // Estado que indica se o token foi carregado

  useEffect(() => {
    const getToken = async () => {
      const token = await AsyncStorage.getItem("userToken");
      setAccessToken(token);
      setIsTokenLoaded(true); // Marca que o token foi carregado
    };

    getToken();
  }, []);

  useEffect(() => {
    if (isTokenLoaded) {
      fetchFilters();
      fetchFilteredTimetable();
    }
  }, [isTokenLoaded, accessToken]);  // Mantém a lógica original

  // Este useEffect agora será chamado toda vez que algum filtro ou data for alterado
  useEffect(() => {
    if (isTokenLoaded) {
      fetchFilteredTimetable();  // Recarregar a tabela quando qualquer filtro ou data for alterado
    }
  }, [selectedTeacher, selectedCourse, selectedSubject, selectedDate, weekDay]);


  const formatDate = (date) => {
  const [year, month, day] = date.split("-");
  return `${day}/${month}/${year}`;
};

const formatWeekDay = (weekday) => {
  const weekdays = {
    "segunda-feira": "Segunda-Feira",
    "terça-feira": "Terça-Feira",
    "quarta-feira": "Quarta-Feira",
    "quinta-feira": "Quinta-Feira",
    "sexta-feira": "Sexta-Feira",
    "sábado": "Sábado",
    "domingo": "Domingo",
  };
  return weekdays[weekday.toLowerCase()] || weekday;
};

  LocaleConfig.locales['pt-br'] = {
    monthNames: [
      'Janeiro',
      'Fevereiro',
      'Março',
      'Abril',
      'Maio',
      'Junho',
      'Julho',
      'Agosto',
      'Setembro',
      'Outubro',
      'Novembro',
      'Dezembro'
    ],
    monthNamesShort: ['Jan.', 'Fev.', 'Mar.', 'Abr.', 'Mai.', 'Jun.', 'Jul.', 'Ago.', 'Set.', 'Out.', 'Nov.', 'Dez.'],
    dayNames: ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'],
    dayNamesShort: ['Dom.', 'Seg.', 'Ter.', 'Qua.', 'Qui.', 'Sex.', 'Sáb.'],
    today: 'Hoje'
  };


  const clearFilters = () => {
    setSelectedTeacher(null);
    setSelectedCourse(null);
    setSelectedSubject(null);
    setSelectedDate(null);
    setFormattedDate(null);
    setWeekDay(null);

    fetchFilteredTimetable(); // Recarrega os dados completos
  };


  const handleDayPress = (day) => {
    const newSelectedDate = parseISO(day.dateString);

    // Formatar data e dia da semana
    const formattedDate = format(newSelectedDate, "yyyy-MM-dd");
    const weekDay = format(newSelectedDate, "EEEE", { locale: ptBR });

    console.log("Data selecionada:", formattedDate);
    console.log("Dia da semana:", weekDay);

    // Atualizar estado
    setSelectedDate(newSelectedDate);
    setFormattedDate(formattedDate);
    setWeekDay(weekDay.toLowerCase());
  };

  LocaleConfig.defaultLocale = 'pt-br';
  const fetchFilteredTimetable = async () => {
    if (!accessToken) {
      return setErrorMessage("Token não encontrado. Por favor, faça login novamente.");
    }
  
    try {
      // Buscar as reservas
      let reservationsData = [];
      try {
        const reservationResponse = await axios.get(`${API_URL}/reservation/`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        reservationsData = reservationResponse.data;
      } catch (error) {
        if (error.response && error.response.status === 404) {
          console.warn("Nenhuma reserva encontrada, exibindo apenas horários fixos.");
        } else {
          throw error; // Repassa outros erros para o catch principal
        }
      }
  
      // Buscar os horários fixos
      const scheduleResponse = await axios.get(`${API_URL}/schedule/`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
  
      const scheduleData = scheduleResponse.data;
  
      let combinedTimetable = [...scheduleData];
  
      // Comparação da data selecionada com a data da reserva
      if (formattedDate || weekDay) {
        const filteredReservations = reservationsData.filter((item) => {
          const reservationDate = item.date;
          const reservationWeekDay = item.weekday ? item.weekday.toLowerCase() : null;
  
          const isDateMatch = reservationDate === formattedDate;  // Compara as datas no formato correto
          const isWeekDayMatch =
            reservationWeekDay && reservationWeekDay === weekDay.toLowerCase();
  
          return isDateMatch || isWeekDayMatch;
        });
  
        // Adiciona as reservas filtradas aos horários fixos
        filteredReservations.forEach((reservation) => {
          const isOverlapping = combinedTimetable.some(
            (item) =>
              item.time.split(" - ")[0] === reservation.time.split(" - ")[0] &&
              item.room === reservation.room
          );
          if (!isOverlapping) {
            combinedTimetable.push({ ...reservation, status: "reserved" });
          }
        });
      }
  
      // Filtra os horários para o dia da semana e o restante dos filtros (curso, disciplina, etc.)
      const filterTimetable = (timetable) => {
        return timetable.filter((item) => {
          const matchesTeacher = selectedTeacher ? item.teacher === selectedTeacher : true;
  
          const [courseNameFromSchedule] = item.course
            ? item.course.split(" - ")
            : [null];
  
          const matchesCourse =
            selectedCourse ? courseNameFromSchedule === selectedCourse : true;
  
          const matchesSubject = selectedSubject ? item.subject === selectedSubject : true;
  
          return matchesTeacher && matchesCourse && matchesSubject;
        });
      };
  
      // Aplica os filtros de data e dia da semana
      combinedTimetable = filterTimetable(combinedTimetable);
  
      // Filtra por semana se o dia da semana foi selecionado
      if (weekDay) {
        combinedTimetable = combinedTimetable.filter((item) => {
          const scheduleWeekDay = item.weekday ? item.weekday.toLowerCase() : null;
          return scheduleWeekDay === weekDay.toLowerCase();
        });
      }
  
      // Atualiza a variável de estado com os horários filtrados
      setTimetable(combinedTimetable);
      console.log("Dados do horário filtrado:", combinedTimetable);
      console.log("Dia da semana selecionado:", weekDay);
    } catch (error) {
      setErrorMessage("Erro ao carregar os dados. Tente novamente mais tarde.");
    }
  };
  
  const fetchFilters = async () => {
    if (!accessToken) {
      return setErrorMessage("Token não encontrado. Por favor, faça login novamente.");
    }

    try {
      const teachersResponse = await axios.get(`${API_URL}/teacher/`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      const subjectsResponse = await axios.get(`${API_URL}/subject/`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      const coursesResponse = await axios.get(`${API_URL}/course/`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      // Organizando os filtros (professores, cursos, disciplinas)
      if (Array.isArray(teachersResponse.data)) {
        const formattedTeachers = teachersResponse.data.map((teacher) => ({
          label: teacher.teacherName || "Professor não disponível", // Garantir que o label nunca seja undefined
          value: teacher.teacherName,
        }));
        setTeachers(formattedTeachers);
      }

      if (Array.isArray(subjectsResponse.data)) {
        const formattedSubjects = subjectsResponse.data.map((subject) => ({
          label: subject.subjectName || "Disciplina não disponível", // Garantir que o label nunca seja undefined
          value: subject.subjectName,
        }));
        setSubjects(formattedSubjects);
      }

      if (Array.isArray(coursesResponse.data)) {
        const formattedCourses = coursesResponse.data.map((course) => ({
          label: course.courseName || "Curso não disponível",
          value: course.courseName,
        }));
        setCourses(formattedCourses);
      }

    } catch (error) {
      setErrorMessage("Erro ao carregar os filtros. Tente novamente mais tarde.");
    }

    console.log("Cursos carregados:", courses);
    console.log("Curso selecionado:", selectedCourse);
    console.log("Tabela filtrada:", timetable);

  };

  const renderTable = () => {
    if (timetable.length === 0) {
      return <Text style={styles.emptyMessage}>Não há horários disponíveis para os filtros selecionados.</Text>;
    }
  
    // Função de formatação de data
    const formatDate = (date) => {
      const [year, month, day] = date.split("-");
      return `${day}/${month}/${year}`;
    };
  
    // Função de formatação de dia da semana
    const formatWeekDay = (weekday) => {
      const weekdays = {
        "segunda-feira": "Segunda-Feira",
        "terça-feira": "Terça-Feira",
        "quarta-feira": "Quarta-Feira",
        "quinta-feira": "Quinta-Feira",
        "sexta-feira": "Sexta-Feira",
        "sábado": "Sábado",
        "domingo": "Domingo",
      };
      return weekdays[weekday.toLowerCase()] || weekday;
    };
  
    return (
      <FlatList
        data={timetable}
        renderItem={({ item }) => {
          const courseParts = item.course ? item.course.split(" - ") : [];
          const courseName = courseParts[0] || "N/A";
          const semester = courseParts[1] || "N/A";
          
          // Formatar o dia da semana ou data
          const formattedWeekDay = item.weekday ? formatWeekDay(item.weekday) : "N/A";
          const formattedDate = item.date ? formatDate(item.date) : "N/A";
  
          return (
            <View style={styles.tableRow}>
              <Text style={styles.tableCell}>{semester}</Text>
              <Text style={styles.tableCell}>{courseName}</Text>
              {/* Exibe o dia da semana ou a data formatada */}
              <Text style={styles.tableCell}>{item.date ? formattedDate : formattedWeekDay}</Text>
              <Text style={styles.tableCell}>{item.teacher || "N/A"}</Text>
              <Text style={styles.tableCell}>{item.subject || "N/A"}</Text>
              <Text style={styles.tableCell}>{item.time || "N/A"}</Text>
              <Text style={styles.tableCell}>{item.room || "N/A"}</Text>
            </View>
          );
        }}
        keyExtractor={(item, index) => item.id ? item.id.toString() : index.toString()}
      />
    );
  };
  

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Pressable style={styles.buttonVoltar} onPress={() => navigation.navigate("HomeScreen")}>
          <Text style={styles.buttonText}>Voltar</Text>
        </Pressable>
        <Image source={require("../assets/fatec-logo.png")} style={styles.logo} />
      </View>

      <Text style={styles.title}>Filtro Específico</Text>
      <Calendar
        onDayPress={handleDayPress}
        markedDates={{
          [selectedDate?.toISOString().split("T")[0]]: {
            selected: true,
            marked: true,
            selectedColor: "#B20000",
          },
        }}
        theme={{
          selectedDayBackgroundColor: "#B20000",
          todayTextColor: "#B20000",
          arrowColor: "#B20000",
        }}
      />

      <TouchableOpacity
        style={styles.clearButton}
        onPress={() => clearFilters()}>
        <Text style={styles.clearButtonText}>Limpar Filtros</Text>
      </TouchableOpacity>

      <RNPickerSelect
        onValueChange={(value) => {
          // Quando o "Selecione um Professor" for escolhido, o valor será null
          setSelectedTeacher(value === "defaultTeacher" ? null : value);
        }}
        items={teachers && teachers.length > 0
          ? teachers.map((teacher, index) => ({
            label: teacher.label || "Curso não disponível",
            value: teacher.value || "defaultTeacher", // Usar "defaultTeacher" como valor para a opção de placeholder
            key: `${teacher.value}-${index}`
          }))
          : [{ label: "Nenhum professor disponível", value: "defaultTeacher" }]}

        placeholder={{ label: "Selecione um Professor", value: "defaultTeacher" }} // Alterar placeholder para "defaultTeacher"
        value={selectedTeacher || "defaultTeacher"}  // Garantir que o valor inicial seja "defaultTeacher"
        style={pickerSelectStyles}
      />

      <RNPickerSelect
        onValueChange={(value) => {
          setSelectedCourse(value === "defaultCourse" ? null : value);
        }}
        items={courses && courses.length > 0
          ? courses.map((course, index) => ({
            label: course.label || "Curso não disponível",
            value: course.value || "defaultCourse",
            key: `${course.value}-${index}`
          }))
          : [{ label: "Nenhum curso disponível", value: "defaultCourse" }]}
        placeholder={{ label: "Selecione um Curso", value: "defaultCourse" }}
        value={selectedCourse || "defaultCourse"}
        style={pickerSelectStyles}
      />




      <RNPickerSelect
        onValueChange={(value) => {
          // Quando o "Selecione uma Disciplina" for escolhido, o valor será null
          setSelectedSubject(value === "defaultSubject" ? null : value);
        }}
        items={subjects && subjects.length > 0
          ? subjects.map((subject, index) => ({
            label: subject.label || "Disciplina não disponível",
            value: subject.value || "defaultSubject", // Usar "defaultSubject" como valor para a opção de placeholder
            key: `${subject.value}-${index}`
          }))
          : [{ label: "Nenhuma disciplina disponível", value: "defaultSubject" }]}

        placeholder={{ label: "Selecione uma Disciplina", value: "defaultSubject" }} // Alterar placeholder para "defaultSubject"
        value={selectedSubject || "defaultSubject"}  // Garantir que o valor inicial seja "defaultSubject"
        style={pickerSelectStyles}
      />

      <View style={styles.table}>
        <View style={styles.tableHeader}>
          <Text style={styles.tableHeaderText}>Semestre</Text>
          <Text style={styles.tableHeaderText}>Curso</Text>
          <Text style={styles.tableHeaderText}>Dia</Text>
          <Text style={styles.tableHeaderText}>Professor</Text>
          <Text style={styles.tableHeaderText}>Disciplina</Text>
          <Text style={styles.tableHeaderText}>Horário</Text>
          <Text style={styles.tableHeaderText}>Sala</Text>
        </View>
        {renderTable()}
      </View>

      {errorMessage && <Text style={styles.errorMessage}>{errorMessage}</Text>}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 20,
  },
  logo: {
    width: 100,
    height: 50,
    resizeMode: "contain",
  },
  buttonVoltar: {
    width: 90,
    height: 40,
    backgroundColor: "#B20000",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 5,
    marginBottom: 15,
  },
  buttonText: {
    fontSize: 18,
    fontFamily: "Roboto-Medium",
    color: "white",
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#333",
    marginBottom: 10,
  },
  table: {
    marginTop: 20,
  },
  tableHeader: {
    flexDirection: "row",
    backgroundColor: "#B20000",
    paddingVertical: 10,
  },
  tableHeaderText: {
    flex: 1,
    color: "#fff",
    textAlign: "center",
  },
  tableRow: {
    flexDirection: "row",
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderBottomColor: "#ddd",
  },
  tableCell: {
    flex: 1,
    textAlign: "center",
  },
  emptyMessage: {
    textAlign: "center",
    marginTop: 20,
    fontSize: 16,
    color: "#B20000",
  },
  errorMessage: {
    textAlign: "center",
    marginTop: 20,
    fontSize: 16,
    color: "red",
  },
  clearButton: {
    backgroundColor: "#B20000",
    padding: 10,
    borderRadius: 5,
    alignItems: "center",
    marginVertical: 10,
  },
  clearButtonText: {
    color: "#fff",
    fontWeight: "bold",
    fontSize: 16,
  },
});

const pickerSelectStyles = StyleSheet.create({
  inputIOS: {
    fontSize: 16,
    paddingVertical: 12,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderColor: 'gray',
    borderRadius: 4,
    color: 'black',
    paddingRight: 30, // para ícones à direita
  },
  inputAndroid: {
    fontSize: 16,
    paddingHorizontal: 10,
    paddingVertical: 8,
    borderWidth: 1,
    borderColor: 'gray',
    borderRadius: 4,
    color: 'black',
    paddingRight: 30,
  },
});