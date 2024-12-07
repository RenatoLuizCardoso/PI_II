import React, { useState, useEffect } from 'react';
import { View, Image, StyleSheet, Text } from 'react-native';
import { useFonts } from 'expo-font';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import axios from 'axios';
import Checkbox from 'expo-checkbox';
import * as ScreenOrientation from 'expo-screen-orientation'; // Importando a biblioteca para controle de orientação
 
import LoginScreen from './screens/LoginScreen';
import HomeScreen from './screens/HomeScreen';
import ForgotPasswordScreen from './screens/ForgotPasswordScreen';
import AutoRegisterScreen from './screens/AutoRegisterScreen';
import SpecificSearchScreen from './screens/SpecificSearchScreen';
import ConfirmPasswordScreen from './screens/ConfirmPassword';
 
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
        setTimeout(() => {
          setExibirTelaLogin(true);
        }, 3000);
      } catch (error) {
        console.error('Erro ao buscar dados do backend:', error);
      }
    };
 
    fetchData();
  }, []);
 
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
  useEffect(() => {
    // Desbloqueia a orientação da tela para permitir rotação para qualquer direção
    const setOrientation = async () => {
      await ScreenOrientation.unlockAsync(); // Permite qualquer orientação (retrato ou paisagem)
    };
    setOrientation();
 
    return () => {
      // Se necessário, você pode bloquear a orientação novamente em alguma tela específica
      // Example: ScreenOrientation.lockAsync(ScreenOrientation.OrientationLock.PORTRAIT_UP)
    };
  }, []);
 
  return (
    <NavigationContainer>
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        <Stack.Screen name="Projeto Integrador" component={LoadingScreen} />
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="HomeScreen" component={HomeScreen} />
        <Stack.Screen name="ForgotPassword" component={ForgotPasswordScreen} />
        <Stack.Screen name="ConfirmPassword" component={ConfirmPasswordScreen} />
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
 