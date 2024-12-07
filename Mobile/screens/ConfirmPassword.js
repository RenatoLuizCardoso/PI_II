import React, { useState, useEffect, useCallback } from "react";
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
import { resetPassword } from "../api/apiService";
import { useFocusEffect } from "@react-navigation/native";
import { Ionicons } from '@expo/vector-icons';  
import axios from "axios";

const ConfirmPassword = ({ navigation }) => {
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [token, setToken] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertVisible, setAlertVisible] = useState(false);
  const [showPassword, setShowPassword] = useState(false); 

  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

  const showAlert = (message, isSuccess = false) => {
    setAlertMessage(message);
    setAlertVisible(true);

    if (isSuccess) {
      setTimeout(() => {
        setAlertVisible(false);
        navigation.navigate("Login"); // Navega para a tela de login após o sucesso
      }, 2000);
    } else {
      setTimeout(() => setAlertVisible(false), 2000);
    }
  };

  if (!fontsLoaded) return null;

  const handleConfirmPassword = async () => {
    const credentials = {
      token: token,
      newPassword: newPassword,
      confirmPassword: confirmPassword,
    };

    // Verificar se as senhas são iguais
    if (newPassword !== confirmPassword) {
      showAlert("As senhas devem ser iguais.", false);
      return;
    }

    if (token == "") {
      showAlert("Digite o código que foi enviado no seu email.", false);
      return;
    }

    if (newPassword == "") {
      showAlert("Digite sua senha.", false);
      return;
    }

    if (confirmPassword == "") {
      showAlert("Digite sua senha.", false);
      return;
    }

    // Enviar a requisição de reset de senha
    setIsLoading(true);
    try {
      const response = await resetPassword(credentials); // Passa o token e a nova senha para o backend
      showAlert("Senha resetada com sucesso!", true);
    } catch (error) {
      showAlert("Erro ao resetar a senha.", false);
    } finally {
      setIsLoading(false);
    }
  };

  useFocusEffect(
    useCallback(() => {
      setNewPassword("");
      setConfirmPassword("");
      setToken("");
    }, [])
  );

  return (
    <View style={styles.container}>
      <Text style={styles.textConfirmPassword}>DEFINIR NOVA SENHA</Text>
      <Image source={require("../assets/profile.png")} style={styles.logo} />
      {alertVisible && (
        <View
          style={[
            styles.alertContainer,
            {
              backgroundColor: alertMessage.startsWith("Senha")
                ? "#d4edda"
                : "#f8d7da",
            },
          ]}
        >
          <Text
            style={[
              styles.alertMessage,
              {
                color: alertMessage.startsWith("Senha") ? "#155724" : "#721c24",
              },
            ]}
          >
            {alertMessage}
          </Text>
        </View>
      )}
      <View style={styles.groupInputs}>
        <Text style={styles.label}>Digite seu código</Text>
        <TextInput
          style={styles.inputPassword}
          placeholder="Digite o código que chegou no seu email: "
          value={token}
          onChangeText={setToken}
        />
        <Text style={styles.label}>Digite sua senha</Text>
        <View style={styles.passwordContainer}>
          <TextInput
            style={styles.inputPassword}
            placeholder="Digite sua nova senha: "
            value={newPassword}
            onChangeText={setNewPassword}
            secureTextEntry={!showPassword}
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
        <Text style={styles.label}>Confirme sua senha</Text>
        <TextInput
          style={styles.inputPassword}
          placeholder="Confirme sua nova senha: "
          value={confirmPassword}
          onChangeText={setConfirmPassword}
          secureTextEntry={true} // Senha oculta
        />
        <TouchableOpacity
          onPress={handleConfirmPassword}
          style={styles.button1}
          disabled={isLoading}
        >
          {isLoading ? (
            <ActivityIndicator size="small" color="white" />
          ) : (
            <Text style={styles.buttonText}>CONFIRMAR</Text>
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
  label: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    marginTop: 10,
    marginBottom: 5,
    color: "#333",
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
  textConfirmPassword: {
    fontSize: 32,
    textAlign: "center",
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
  inputPassword: {
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

export default ConfirmPassword;
