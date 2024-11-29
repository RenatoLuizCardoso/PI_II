import React, { useState, useEffect } from "react";
import {
  View,
  Image,
  TextInput,
  StyleSheet,
  Text,
  TouchableOpacity,
  ActivityIndicator
} from "react-native";
import { useFonts } from "expo-font";
import Checkbox from 'expo-checkbox';
import AsyncStorage from "@react-native-async-storage/async-storage";
import { Ionicons } from '@expo/vector-icons';  
import { loginStudent } from '../api/apiService'; // Importando a função que faz o login

export default function LoginScreen({ navigation }) {
  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

  const [institutionalEmail, setEmail] = useState("");
  const [studentPassword, setPassword] = useState("");
  const [rememberLogin, setRememberLogin] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false); 
  const [alertMessage, setAlertMessage] = useState(""); // Mensagem de alerta
  const [alertVisible, setAlertVisible] = useState(false); // Controle de visibilidade do alerta

  // Função de validação dos campos
  const validateLoginFields = () => {
    if (!institutionalEmail || !studentPassword) {
      showAlert("Por favor, preencha o e-mail e a senha.");
      return false;
    }
    return true;
  };

   // Função para exibir mensagens de alerta e ocultá-las após 3 segundos
   const showAlert = (message) => {
    setAlertMessage(message);
    setAlertVisible(true);
    setTimeout(() => setAlertVisible(false), 10000); // Alerta desaparece após 3 segundos
  };

  // Função de login
  const login = async () => {
    if (!validateLoginFields()) {
      return;
    }

    setIsLoading(true);

    const credentials = {
      institutionalEmail: institutionalEmail,
      studentPassword: studentPassword,
    };

    try {
      const response = await loginStudent(credentials); // Envia as credenciais para a API


      if (response) {
        showAlert("Sucesso, login bem-sucedido!");

        await AsyncStorage.setItem("userToken", response.access_token);

        // Navega para a tela principal com as informações do usuário
        navigation.navigate("HomeScreen"); // Passando 'user' para HomeScreen
      }
    } catch (error) {
      console.error("Erro ao tentar fazer login", error);
      showAlert("Erro ao tentar fazer login.");
    } finally {
      setIsLoading(false);
    }
  };

  // Aguarda o carregamento das fontes
  if (!fontsLoaded) {
    return null;
  }

  return (
    <View style={styles.container}>
      <Text style={styles.textLogin}>LOGIN</Text>
      <Image source={require("../assets/profile.png")} style={styles.logo} />
      
      {/* Alerta de erro ou sucesso */}
      {alertVisible && (
        <View style={styles.alertContainer}>
          <Text style={styles.alertMessage}>{alertMessage}</Text>
        </View>
      )}

      <Text style={styles.label}>Email institucional</Text>
      <TextInput
        style={styles.input}
        placeholder="Digite seu email:"
        value={institutionalEmail}
        onChangeText={setEmail}
        autoCapitalize="none"
        inputMode="email-address"
      />

      <Text style={styles.label}>Senha</Text>
      <View style={styles.passwordContainer}>
        <TextInput
          style={styles.input}
          placeholder="Digite sua senha:"
          secureTextEntry={!showPassword} 
          value={studentPassword}
          onChangeText={setPassword}
        />
        <TouchableOpacity
          onPress={() => setShowPassword(!showPassword)}
          style={styles.eyeButton}
        >
          <Ionicons
          style={styles.icon}
            name={showPassword ? "eye-off" : "eye"}
            size={24}
            color="gray"
          />
        </TouchableOpacity>
      </View>

      <View style={styles.optionsContainer}>
        <TouchableOpacity onPress={() => navigation.navigate("ForgotPassword")}>
          <Text style={styles.forgotPassword}>Esqueci minha senha</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.optionsContainer}>
        <TouchableOpacity onPress={() => navigation.navigate("ConfirmPassword")}>
          <Text style={styles.ConfirmPassword}>Esqueci minha senha</Text>
        </TouchableOpacity>
      </View>

      <TouchableOpacity style={styles.button} onPress={login} disabled={isLoading}>
      {isLoading ? (
            <ActivityIndicator size="small" color="white" />
          ) : (
            <Text style={styles.buttonText}>ENTRAR</Text>
          )}
      </TouchableOpacity>

      <TouchableOpacity onPress={() => navigation.navigate("Register")}>
        <Text style={styles.signUp}>Não tem uma conta? Cadastre-se</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    paddingHorizontal: 20,
  },
  textLogin: {
    fontSize: 32,
    fontFamily: "Roboto-Regular",
    marginBottom: 20,
  },
  logo: {
    width: 150,
    height: 150,
    marginBottom: 10,
  },
  label: {
    fontSize: 18,
    fontFamily: "Roboto-Regular",
    alignSelf: "flex-start",
    marginBottom: 5,
  },
  input: {
    width: 300,
    height: 40,
    borderWidth: 1,
    borderColor: "#ccc",
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 15,
    fontSize: 16,
    fontFamily: "Roboto-Regular",
  },
  passwordContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    width: '100%',
  },
  eyeButton: {
    position: 'absolute',
    right: 10,
  },
  optionsContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "100%",
    marginBottom: 15,
  },
  rememberLogin: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    marginLeft: 5,
  },
  forgotPassword: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    color: "red",
    textDecorationLine: "underline",
  },
  button: {
    width: 120,
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
  signUp: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    color: "red",
    textDecorationLine: "underline",
    marginBottom: 85,
  },
  alertContainer: {
    backgroundColor: "#f8d7da",
    padding: 10,
    borderRadius: 5,
    marginBottom: 15,
  },
  alertMessage: {
    color: "#721c24",
    fontSize: 16,
    fontFamily: "Roboto-Regular",
  },
  icon:{
    marginBottom : 15
  }
});
