import React, { useState, useEffect } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { View, Image, StyleSheet, Text, Dimensions, TouchableOpacity, ScrollView } from "react-native";
import { useFonts } from "expo-font";
import AwesomeAlert from "react-native-awesome-alerts";
import axios from "axios";



const { width } = Dimensions.get('window');

export default function HomeScreen({ navigation, route }) {
  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

  const [userName, setUserName] = useState("");
  const [showAlert, setShowAlert] = useState(false);

  useEffect(() => {
    const fetchUserName = async () => {
      try {
        
        const token = await AsyncStorage.getItem("userToken");

        const response = await axios.get('https://projeto-integrador-1v4i.onrender.com/student/',{
          headers: {
            'Authorization': `Bearer ${token}`
          }
        })

        username = response.data.studentName;

        if (response.status === 200) {
          setUserName(username);
        } else {
          setUserName('Usuário');
        }
      } catch (error) {
        console.error("Erro ao obter nome de usuário: ", error);
        setUserName("Usuário");
      }
    };

    fetchUserName();
  }, []);
  

  const logout = async () => {
    await AsyncStorage.removeItem("userToken");
    navigation.navigate("Login");
  };

  const handleLogout = () => {
    setShowAlert(true);
  };

  const handleConfirmLogout = () => {
    setShowAlert(false);
    logout();
  };

  const handleCancelLogout = () => {
    setShowAlert(false);
  };

  // Verifique se user ou fontsLoaded são falsos
if (!fontsLoaded || !userName) {
  console.log("Carregando... fontsLoaded:", fontsLoaded, "user:", userName);
  return (
    <View style={styles.container}>
      <Text>Carregando...</Text>
    </View>
  );
}

  return (
    <View style={styles.container}>
      <ScrollView contentContainerStyle={styles.scrollContainer}>
        <View style={styles.header}>
          <TouchableOpacity style={styles.logoutButton} onPress={handleLogout}>
            <Text style={styles.logoutButtonText}>Sair</Text>
          </TouchableOpacity>
          <Image source={require("../assets/fatec-logo.png")} style={styles.logo} />
        </View>

        <Text style={styles.title}>Seja Bem-Vindo, {userName}!</Text>

        <Text style={styles.textnovidade}>Destaques</Text>

        {/* Cards com informações */}
        <View style={styles.cardContainer}>
          <View style={styles.card}>
            <Text style={styles.cardTitle}>Próximo Evento</Text>
            <Text style={styles.cardContent}>Semana da Tecnologia - 25 de Outubro</Text>
          </View>

          <View style={styles.card}>
            <Text style={styles.cardTitle}>Aviso Importante</Text>
            <Text style={styles.cardContent}>Inscrições para Monitoria abertas até 30 de Outubro</Text>
          </View>

          <View style={styles.card}>
            <Text style={styles.cardTitle}>Dica do Dia</Text>
            <Text style={styles.cardContent}>Confira os tutoriais sobre React Native na nossa plataforma de ensino</Text>
          </View>
        </View>

        <View style={styles.footer}>
          <TouchableOpacity
            style={styles.button}
            onPress={() => navigation.navigate("Search")}
          >
            <Text style={styles.buttonText}>Grade de Horários</Text>
          </TouchableOpacity>
        </View>

        <AwesomeAlert
          show={showAlert}
          showProgress={false}
          title="Confirmação"
          message="Você realmente deseja sair?"
          closeOnTouchOutside={false}
          closeOnHardwareBackPress={false}
          showCancelButton={true}
          showConfirmButton={true}
          cancelText="Cancelar"
          confirmText="Sair"
          cancelButtonColor="#000"
          confirmButtonColor="#B20000"
          onCancelPressed={handleCancelLogout}
          onConfirmPressed={handleConfirmLogout}
        />
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f4f4f4",
    marginTop: 15,
  },
  scrollContainer: {
    paddingBottom: 40,
    alignItems: "center",
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 30,
    width: '100%',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
  },
  logoutButton: {
    padding: 10,
    backgroundColor: "#B20000",
    borderRadius: 5,
  },
  logoutButtonText: {
    color: 'white',
    fontSize: 16,
    fontFamily: "Roboto-Medium",
  },
  logo: {
    width: 150,
    height: 80,
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    marginTop: 50,
    marginBottom: 20,
    color: "#333",
  },
  textnovidade: {
    marginTop: 30,
    fontSize: 28,
    fontFamily: "Roboto-Medium",
    color: "black",
  },
  cardContainer: {
    marginTop: 30,
    width: '90%',
  },
  card: {
    backgroundColor: "#FFF",
    padding: 15,
    borderRadius: 10,
    marginBottom: 15,
    // A sombra no iOS
    boxShadow: "0px 2px 5px rgba(0, 0, 0, 0.2)", // Adicionando boxShadow
    elevation: 3, // Mantém a sombra no Android com o elevation
  },
  cardTitle: {
    fontSize: 18,
    fontFamily: "Roboto-Medium",
    color: "#B20000",
    marginBottom: 10,
  },
  cardContent: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    color: "#333",
  },
  footer: {
    marginTop: 110,
    width: '100%',
    paddingHorizontal: 20,
  },
  button: {
    width: "100%",
    height: 60,
    backgroundColor: "#B20000",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 5,
    marginBottom: 50,
  },
  buttonText: {
    fontSize: 18,
    fontFamily: "Roboto-Medium",
    color: "white",
  },
});
