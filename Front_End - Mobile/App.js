import React, { useState, useEffect } from 'react';
import { View, Image, StyleSheet, Text, Button } from 'react-native';
import { useFonts } from 'expo-font';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import axios from 'axios'; // Importar Axios para fazer requisições HTTP
import Checkbox from 'expo-checkbox';

import LoginScreen from './screens/LoginScreen';
import HomeScreen from './screens/HomeScreen';
import SearchScreen from './screens/SearchScreen';
import ForgotPasswordScreen from './screens/ForgotPasswordScreen';
import AutoRegisterScreen from './screens/AutoRegisterScreen';
import SpecificSearchScreen from './screens/SpecificSearchScreen';

const Stack = createNativeStackNavigator();

function LoadingScreen({ navigation }) {
  const [exibirTelaLogin, setExibirTelaLogin] = useState(false);

  const [fontsLoaded] = useFonts({
    'Roboto-Light': require('./assets/fonts/Roboto-Light.ttf'),
    'Roboto-Regular': require('./assets/fonts/Roboto-Regular.ttf'),
    'Roboto-Medium': require('./assets/fonts/Roboto-Medium.ttf'),
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Aqui você faria a requisição GET para verificar se há dados de usuário salvos no backend
        // Simulação: Setar exibirTelaLogin como true após 3 segundos
        setTimeout(() => {
          setExibirTelaLogin(true);
        }, 3000);
      } catch (error) {
        console.error('Erro ao buscar dados do backend:', error);
        // Trate o erro conforme necessário
      }
    };

    fetchData();
  }, []);

  const registrarUsuario = async () => {
    try {
      // Simulação de dados de usuário a serem enviados para o backend
      const dadosUsuario = {
        nome: 'Exemplo',
        email: 'exemplo@email.com',
        senha: 'senha123',
      };

      // Simulação de requisição POST para registrar usuário no backend
      const response = await axios.post('http://seu-backend-url.com/api/usuarios', dadosUsuario);
      console.log('Usuário registrado com sucesso:', response.data);

      // Após registrar o usuário, redirecionar para a tela de login
      navigation.navigate('Login');
    } catch (error) {
      console.error('Erro ao registrar usuário:', error);
      // Trate o erro conforme necessário
    }
  };

  if (!fontsLoaded) {
    return null;
  }

  return (
    <View style={styles.container}>
      {exibirTelaLogin ? (
        <LoginScreen navigation={navigation} />
      ) : (
        <View style={styles.loadingScreen}>
          <Text style={styles.title}>Grade de Horário</Text>
          <Text style={styles.subtitle}>Aplicativo do Aluno</Text>
          <Image
            source={require('./assets/fatec-logo.png')}
            style={styles.logoFatec}
          />
          <Image
            source={require('./assets/cps-logo.png')}
            style={styles.logoCps}
          />
         
        </View>
      )}
    </View>
  );
}

function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        <Stack.Screen name="Projeto Integrador" component={LoadingScreen} />
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="HomeScreen" component={HomeScreen} />
        <Stack.Screen name="SearchScreen" component={SearchScreen} />
        <Stack.Screen
          name="ForgotPassword"
          component={ForgotPasswordScreen}
        />
        <Stack.Screen name="Register" component={AutoRegisterScreen} />
        <Stack.Screen name="Search" component={SpecificSearchScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontSize: 24,
    fontFamily: 'Roboto-Regular',
    marginTop: 10,
  },
  subtitle: {
    fontSize: 18,
    fontFamily: 'Roboto-Regular',
    marginTop: 10,
  },
  loadingScreen: {
   
    alignItems: 'center',
    justifyContent: 'center',
  },
  logoFatec: {
    width: 200,
    height: 100,
    marginTop: 20,
  },
  logoCps: {
    width: 110,
    height: 70,
    marginTop: 20,
  },
});

export default App;
