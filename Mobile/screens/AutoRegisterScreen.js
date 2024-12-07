import React, { useState, useCallback } from "react";
import {
  View,
  Image,
  TextInput,
  StyleSheet,
  Text,
  Pressable,
  ActivityIndicator,
  TouchableOpacity,
} from "react-native";
import { useFonts } from "expo-font";
import { useFocusEffect } from "@react-navigation/native";
import { registerStudent } from "../api/apiService";
import { Ionicons } from "@expo/vector-icons";
import axios from "axios";

export default function AutoRegisterScreen({ navigation }) {
  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

  const [studentName, setNome] = useState("");
  const [institutionalEmail, setEmail] = useState("");
  const [studentPassword, setSenha] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [showPassword, setShowPassword] = useState(false);

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

  const validateFields = () => {
    const trimmedNome = studentName.trim();
    const trimmedEmail = institutionalEmail.trim();
    const trimmedSenha = studentPassword.trim();

    // Regex para permitir letras, espaços, acentos e cedilha
    const nameRegex = /^[A-Za-zÀ-ÿ\s]+$/;

    if (!trimmedNome || !nameRegex.test(trimmedNome)) {
      showAlert(
        "Por favor, digite o seu nome completo."
      );
      return false;
    }

    if (!trimmedEmail.endsWith("@fatec.sp.gov.br")) {
      showAlert("Por favor, o email precisa ser institucional.");
      return false;
    }

    if (trimmedSenha === "") {
      showAlert("Por favor, digite uma senha.");
      return false;
    }

    return true;
  };

  const cadastro = async () => {
    if (!validateFields()) {
      return;
    }

    setIsLoading(true);

    const data = {
      studentName: studentName.trim(),
      institutionalEmail: institutionalEmail.trim(),
      studentPassword: studentPassword,
    };

    try {
      const response = await registerStudent(data);

      if (response) {
        showAlert("Cadastro realizado com sucesso!", true); // Passa `true` para indicar sucesso
      }
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 404) {
        showAlert("Erro: não foi possível realizar o cadastro.");
      } else {
        showAlert(
          "Ocorreu um erro ao tentar cadastrar o aluno. Tente novamente mais tarde."
        );
      }
    } finally {
      setIsLoading(false);
    }
  };

  useFocusEffect(
    useCallback(() => {
      setNome("");
      setEmail("");
      setSenha("");
    }, [])
  );

  if (!fontsLoaded) {
    return null;
  }

  return (
    <View style={styles.container}>
      <Image source={require("../assets/fatec-logo.png")} style={styles.logo} />

      {/* Alerta de erro ou sucesso */}
      {alertVisible && (
        <View
          style={[
            styles.alertContainer,
            {
              backgroundColor: alertMessage.startsWith("Cadastro")
                ? "#d4edda"
                : "#f8d7da",
            },
          ]}
        >
          <Text
            style={[
              styles.alertMessage,
              {
                color: alertMessage.startsWith("Cadastro")
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
        <Text style={styles.label}>Nome Completo</Text>
        <TextInput
          style={styles.input}
          placeholder="Digite seu nome"
          onChangeText={(text) => setNome(text)}
          value={studentName}
        />
        <Text style={styles.label}>Email institucional</Text>
        <TextInput
          style={styles.input}
          placeholder="Digite seu email"
          onChangeText={(text) => setEmail(text.trim())}
          value={institutionalEmail}
        />
        <Text style={styles.label}>Senha</Text>
        <View style={styles.passwordContainer}>
          <TextInput
            style={styles.input}
            placeholder="Digite sua senha"
            secureTextEntry={!showPassword}
            value={studentPassword}
            onChangeText={(text) => setSenha(text.trim())}
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
        <Pressable
          style={styles.button}
          onPress={cadastro}
          disabled={isLoading}
        >
          {isLoading ? (
            <ActivityIndicator size="small" color="white" />
          ) : (
            <Text style={styles.buttonText}>CADASTRAR</Text>
          )}
        </Pressable>
        <Pressable
          style={styles.buttonVoltar}
          onPress={() => navigation.navigate("Login")}
        >
          <Text style={styles.buttonText}>VOLTAR</Text>
        </Pressable>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#fff",
    paddingHorizontal: 20,
  },
  groupInputs: {
    width: "100%",
  },
  input: {
    width: "100%",
    height: 50,
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
  icon:{
    marginBottom : 15
  },
  logo: {
    width: 200,
    height: 100,
    marginBottom: 10,
    resizeMode: "contain",
  },
  label: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    marginTop: 10,
    marginBottom: 5,
    color: "#333",
  },
  button: {
    backgroundColor: "#B20000",
    paddingVertical: 12,
    borderRadius: 5,
    alignItems: "center",
    marginTop: 20,
  },
  buttonVoltar: {
    backgroundColor: "#000",
    paddingVertical: 12,
    borderRadius: 5,
    alignItems: "center",
    marginTop: 10,
  },
  buttonText: {
    color: "#fff",
    fontFamily: "Roboto-Medium",
    fontSize: 16,
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
