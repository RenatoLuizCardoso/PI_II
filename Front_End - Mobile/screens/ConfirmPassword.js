import React, { useState, useCallback } from "react";
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Image, ActivityIndicator } from "react-native";
import { useFonts } from "expo-font";
import { useFocusEffect } from "@react-navigation/native";
import { forgotPassword } from "../api/apiService";
import axios from "axios";

const ConfirmPassword = ({ navigation }) => {
  const [password, setPassword] = useState(""); // Estado para a senha
  const [confirmPassword, setConfirmPassword] = useState(""); // Estado para confirmar senha
  const [isLoading, setIsLoading] = useState(false);
  const [alertMessage, setAlertMessage] = useState(""); 
  const [alertVisible, setAlertVisible] = useState(false); 

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

  const handleConfirmPassword = () => {
    // Verificar se as senhas são iguais
    if (password !== confirmPassword) {
      showAlert("As senhas devem ser iguais.", false);
      return;
    }

    // Aqui você pode adicionar a lógica para enviar a senha ou outro processo
    showAlert("Senha resetada com sucesso.", true);
  };
  useFocusEffect(
    useCallback(() => {
      setPassword("");
      setConfirmPassword("");
    }, [])
  );


  return (
    <View style={styles.container}>
      <Text style={styles.textRecoverPassword}>CONFIRMAR NOVA SENHA</Text>
      <Image source={require("../assets/profile.png")} style={styles.logo} />
      {alertVisible && (
        <View style={[styles.alertContainer, { backgroundColor: alertMessage.startsWith("Senha") ? "#d4edda" : "#f8d7da" }]}>
          <Text style={[styles.alertMessage, { color: alertMessage.startsWith("Senha") ? "#155724" : "#721c24" }]}>
            {alertMessage}
          </Text>
        </View>
      )}
      <View style={styles.groupInputs}>
        <Text style={styles.studentPassword}>Digite sua senha</Text>
        <TextInput 
          style={styles.inputPassword} 
          placeholder="Digite sua senha: " 
          value={password} 
          onChangeText={setPassword} 
          secureTextEntry={true} // Senha oculta
        />
        <Text style={styles.studentPassword}>Confirme sua senha</Text>
        <TextInput 
          style={styles.inputPassword} 
          placeholder="Confirme sua senha: " 
          value={confirmPassword} 
          onChangeText={setConfirmPassword} 
          secureTextEntry={true} // Senha oculta
        />
        <TouchableOpacity onPress={handleConfirmPassword} style={styles.button1} disabled={isLoading}>
          {isLoading ? <ActivityIndicator size="small" color="white" /> : <Text style={styles.buttonText}>CONFIRMAR</Text>}
        </TouchableOpacity>
        <TouchableOpacity onPress={() => navigation.goBack()} style={styles.button2}>
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
  studentPassword:{
    fontSize: 20,
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
  inputPassword: {
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

export default ConfirmPassword;
