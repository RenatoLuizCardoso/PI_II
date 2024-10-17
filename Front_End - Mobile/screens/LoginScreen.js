import React, { useState, useEffect } from "react";
import {
  View,
  Image,
  TextInput,
  StyleSheet,
  Text,
  TouchableOpacity,
  CheckBox,
  Alert,
} from "react-native";
import { useFonts } from "expo-font";
import Checkbox from 'expo-checkbox';
import AsyncStorage from "@react-native-async-storage/async-storage";
import { Ionicons } from '@expo/vector-icons';  // Importar ícones

const API_URL = "http://192.168.15.49:3000/students";

export default function LoginScreen({ navigation }) {
  const [fontsLoaded] = useFonts({
    "Roboto-Light": require("../assets/fonts/Roboto-Light.ttf"),
    "Roboto-Regular": require("../assets/fonts/Roboto-Regular.ttf"),
    "Roboto-Medium": require("../assets/fonts/Roboto-Medium.ttf"),
  });

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [rememberLogin, setRememberLogin] = useState(false);
  const [showPassword, setShowPassword] = useState(false); // Novo estado

  useEffect(() => {
    checkRememberedLogin();
  }, []);

  const checkRememberedLogin = async () => {
    try {
      const rememberedLogin = await AsyncStorage.getItem("rememberedLogin");
      if (rememberedLogin) {
        const user = JSON.parse(rememberedLogin);
        setEmail(user.email);
        setPassword(user.password);
        setRememberLogin(true);
      }
    } catch (error) {
      console.error("Erro ao recuperar o estado de lembrar login", error);
    }
  };

  const login = async () => {
    try {
      const response = await fetch(API_URL);
      const students = await response.json();
      const user = students.find((student) => student.email === email);

      if (!user || user.password !== password) {
        alert("Erro, email ou senha incorretos.");
        return;
      }

      alert("Sucesso, login bem-sucedido!");

      if (rememberLogin) {
        await AsyncStorage.setItem("rememberedLogin", JSON.stringify(user));
      } else {
        await AsyncStorage.removeItem("rememberedLogin");
      }

      navigation.navigate("HomeScreen", { user });

    } catch (error) {
      console.error("Erro ao tentar fazer login", error);
      alert("Erro ao tentar fazer login.");
    }
  };

  if (!fontsLoaded) {
    return null;
  }

  return (
    <View style={styles.container}>
      <Text style={styles.textLogin}>Login</Text>
      <Image source={require("../assets/profile.png")} style={styles.logo} />
      <Text style={styles.label}>Email institucional</Text>
      <TextInput
        style={styles.input}
        placeholder="Digite seu email"
        value={email}
        onChangeText={setEmail}
        autoCapitalize="none"
        keyboardType="email-address"
      />

      <Text style={styles.label}>Senha</Text>
      <View style={styles.passwordContainer}>
        <TextInput
          style={styles.input}
          placeholder="Digite sua senha"
          secureTextEntry={!showPassword} 
          value={password}
          onChangeText={setPassword}
        />
        <TouchableOpacity
          onPress={() => setShowPassword(!showPassword)}
          style={styles.eyeButton}
        >
          <Ionicons
            name={showPassword ? "eye-off" : "eye"} // Ícone de olho
            size={24}
            color="gray"
          />
        </TouchableOpacity>
      </View>

      <View style={styles.optionsContainer}>
        <View style={styles.section}>
          <Checkbox style={styles.checkbox} value={rememberLogin} onValueChange={setRememberLogin} />
          <Text style={styles.rememberLogin}>Lembrar meu login?</Text>
        </View>
        <TouchableOpacity onPress={() => navigation.navigate("ForgotPassword")}>
          <Text style={styles.forgotPassword}>Esqueci minha senha</Text>
        </TouchableOpacity>
      </View>

      <TouchableOpacity style={styles.button} onPress={login}>
        <Text style={styles.buttonText}>ENTRAR</Text>
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
  checkboxContainer: {
    flexDirection: "row",
    alignItems: "center",
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
  signUp: {
    fontSize: 16,
    fontFamily: "Roboto-Regular",
    color: "red",
    textDecorationLine: "underline",
    marginBottom: 85,
  },
});
