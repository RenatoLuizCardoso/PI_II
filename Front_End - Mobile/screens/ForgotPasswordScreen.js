import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Image,
  ActivityIndicator,
} from "react-native";
import { useFonts } from "expo-font";
import { forgotPassword } from "../api/apiService";
import axios from "axios";

const ForgotPasswordScreen = ({ navigation }) => {
  const [email, setEmail] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [alertMessage, setAlertMessage] = useState(""); // Mensagem de alerta
  const [alertVisible, setAlertVisible] = useState(false); // Controle de visibilidade do alerta

  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

 // Função para exibir mensagens de alerta e ocultá-las após 3 segundos
 const showAlert = (message, isSuccess = false) => {
  setAlertMessage(message);
  setAlertVisible(true);

  if (isSuccess) {
    setTimeout(() => {
      setAlertVisible(false);
      navigation.navigate("Login"); // Navega para a tela de login após o sucesso
    }, 3000); // Alerta desaparece após 3 segundos
  } else {
    setTimeout(() => setAlertVisible(false), 3000);
  }
};

  if (!fontsLoaded) {
    return null;
  }

  const handleForgotPassword = async () => {
    if (!email.endsWith("@fatec.sp.gov.br")) {
      showAlert("Por favor, insira um e-mail institucional válido.");
      return;
    }
  
    setIsLoading(true);
  
    try {
      const response = await forgotPassword(email);
  
      if (response) {
        showAlert("Um e-mail de recuperação foi enviado.", true);
      } else {
        showAlert("Ocorreu um erro ao tentar recuperar a senha. Tente novamente mais tarde.");
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
  
      if (axios.isAxiosError(error)) {
        showAlert("Ocorreu um erro inesperado. Tente novamente.");
      } else {
        showAlert("Erro ao tentar recuperar a senha.");
      }
    } finally {
      setIsLoading(false);
    }
  };
  

  

  return (
    <View style={styles.container}>
      <Text style={styles.textRecoverPassword}>RECUPERAR SENHA</Text>
      <Image source={require("../assets/profile.png")} style={styles.logo} />

      {/* Alerta de erro ou sucesso */}
      {alertVisible && (
        <View
          style={[
            styles.alertContainer,
            {
              backgroundColor: alertMessage.startsWith("Um")
                ? "#d4edda"
                : "#f8d7da",
            },
          ]}
        >
          <Text
            style={[
              styles.alertMessage,
              {
                color: alertMessage.startsWith("Um")
                  ? "#155724"
                  : "#721c24",
              },
            ]}
          >
            {alertMessage}
          </Text>
        </View>
      )}
      <View style={styles.groupInputs}>
        <Text style={styles.emailInstitucional}>Email institucional</Text>
        <TextInput
          style={styles.inputEmail}
          placeholder="Digite seu email: "
          value={email}
          onChangeText={setEmail}
          inputMode="email-address"
          autoCapitalize="none"
          autoCorrect={false}
        />
        <TouchableOpacity
          onPress={handleForgotPassword}
          style={styles.button1}
          disabled={isLoading}
        >
          {isLoading ? (
            <ActivityIndicator size="small" color="white" />
          ) : (
            <Text style={styles.buttonText}>ENVIAR</Text>
          )}
        </TouchableOpacity>
        <TouchableOpacity
          onPress={() => navigation.goBack()}
          style={styles.button2}
        >
          <Text style={styles.buttonText}>VOLTAR</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
  },
  textRecoverPassword: {
    fontSize: 18,
    marginBottom: 20,
    fontFamily: "Roboto-Regular",
  },
  logo: {
    width: 150,
    height: 150,
    marginBottom: 20,
  },
  groupInputs: {
    width: "100%",
  },
  emailInstitucional: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    marginBottom: 10,
  },
  inputEmail: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    height: 50,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderColor: "#CCC",
    borderRadius: 5,
    marginBottom: 20,
  },
  button1: {
    height: 50,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 5,
    backgroundColor: "#B20000",
    marginBottom: 10,
    width: "100%",
  },
  button2: {
    height: 50,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 5,
    backgroundColor: "#141414",
    width: "100%",
  },
  buttonText: {
    fontSize: 16,
    fontFamily: "Roboto-Medium",
    color: "white",
  },
  alertContainer: {
    padding: 10,
    borderRadius: 5,
    marginBottom: 15,
  },
  alertMessage: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
  },
});

export default ForgotPasswordScreen;
