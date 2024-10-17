import React, { useState, useEffect } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { View, Image, StyleSheet, Text, Dimensions, TouchableOpacity, ScrollView } from "react-native";
import { useFonts } from "expo-font";
import Swiper from 'react-native-swiper';
import AwesomeAlert from "react-native-awesome-alerts";
import { Ionicons } from '@expo/vector-icons';

const { width } = Dimensions.get('window');
export default function HomeScreen({ navigation, route }) {
  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

  const [user, setUser] = useState(null); 
  const [showAlert, setShowAlert] = useState(false);

  useEffect(() => {
    if (route.params && route.params.user) {
      setUser(route.params.user);
    }
  }, [route.params]);

  const logout = async () => {
    try {
      await AsyncStorage.removeItem("rememberedLogin");
      navigation.navigate("Login");
    } catch (error) {
      console.error("Erro ao tentar fazer logout", error);
    }
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

  if (!fontsLoaded || !user) {
    return null;
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

        <Text style={styles.title}>Seja Bem-Vindo, {user.name}</Text>

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
  swiper: {
    height: 200,
  },
  textnovidade: {
    marginTop: 30,
    fontSize: 28,
    fontFamily: "Roboto-Medium",
    color: "black",
  },
  swiperImage: {
    width: width * 0.9,
    height: 200,
    borderRadius: 10,
  },
  buttonWrapper: {
    backgroundColor: 'transparent',
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 10,
    paddingVertical: 20,
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
    shadowColor: "#000",
    shadowOpacity: 0.2,
    shadowOffset: { width: 0, height: 2 },
    shadowRadius: 5,
    elevation: 3,
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
  },
  buttonText: {
    fontSize: 18,
    fontFamily: "Roboto-Medium",
    color: "white",
  },
});
