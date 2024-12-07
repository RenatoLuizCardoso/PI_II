import React from 'react';
import { View, Text, TextInput } from 'react-native';

export default function TelaSenha() {
  return (
    <View style={styles.container}>
      <Text>Tela Senha</Text>
      <TextInput style={styles.input}>TELA LOGIN TESTE</TextInput>
    </View>
  );
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
