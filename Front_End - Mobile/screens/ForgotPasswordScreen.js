import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Image, Alert } from 'react-native';
import { useFonts } from 'expo-font';

// Simulação do banco de dados JSON
const usersDatabase = [
  { id: 1, email: 'user1@example.com', password: 'password123' },
  { id: 2, email: 'user2@example.com', password: 'mypassword' },
  // Outros usuários podem ser adicionados aqui
];

const ForgotPasswordScreen = ({ navigation }) => {
  const [email, setEmail] = useState('');

  const [fontsLoaded] = useFonts({
    'Roboto-Light': require('../assets/fonts/Roboto-Light.ttf'),
    'Roboto-Regular': require('../assets/fonts/Roboto-Regular.ttf'),
    'Roboto-Medium': require('../assets/fonts/Roboto-Medium.ttf'),
  });

  if (!fontsLoaded) {
    return null;
  }

  const handleForgotPassword = () => {
    // Verifica se o e-mail existe no banco de dados
    const userExists = usersDatabase.some(user => user.email === email);

    if (userExists) {
      Alert.alert('Sucesso', `Email de recuperação enviado para ${email}`);
      // Lógica de envio de email de recuperação
    } else {
      Alert.alert('Erro', 'Email não encontrado. Verifique e tente novamente.');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.textRecoverPassword}>RECUPERAR SENHA</Text>
      <Image source={require("../assets/profile.png")} style={styles.logo} />
      <View style={styles.groupInputs}>
        <Text style={styles.emailInstitucional}>Email institucional</Text>
        <TextInput
          style={styles.inputEmail}
          placeholder="Digite seu email: "
          value={email}
          onChangeText={(text) => setEmail(text)}
          keyboardType="email-address"
          autoCapitalize="none"
          autoCorrect={false}
        />
        <TouchableOpacity
          onPress={handleForgotPassword}
          style={styles.button1}
        >
          <Text style={styles.buttonText}>ENVIAR</Text>
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
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  textRecoverPassword: {
    fontSize: 18,
    marginBottom: 20,
    fontFamily: 'Roboto-Regular',
  },
  logo: {
    width: 150,
    height: 150,
    marginBottom: 20,
  },
  groupInputs: {
    width: '100%',
  },
  emailInstitucional: {
    fontSize: 16,
    fontFamily: 'Roboto-Regular',
    marginBottom: 10,
  },
  inputEmail: {
    fontSize: 16,
    fontFamily: 'Roboto-Regular',
    height: 50,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderColor: "#CCC",
    borderRadius: 5,
    marginBottom: 20,
  },
  button1: {
    height: 50,
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 5,
    backgroundColor: '#B20000',
    marginBottom: 10,
    width: '100%',
  },
  button2: {
    height: 50,
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 5,
    backgroundColor: '#141414',
    width: '100%',
  },
  buttonText: {
    fontSize: 16,
    fontFamily: 'Roboto-Medium',
    color: 'white',
  },
});

export default ForgotPasswordScreen;
