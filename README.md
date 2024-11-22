# Sistema de Gestão e Visualização de Reservas de Sala 
 
Este projeto visa otimizar a reserva de espaços educacionais, como laboratórios e salas de aula, facilitando a gestão de horários, notificações e comunicação entre alunos, professores e administradores.
 
---
 
## 📋 Dores Identificadas
 
- **Conflitos de agendamento**: Problemas com a reserva de espaços.
- **Falta de transparência**: Informação insuficiente sobre disponibilidade de recursos.
- **Comunicação ineficiente**: Ausência de notificações claras sobre alterações ou cancelamentos.
 
---
 
## 🎯 Objetivo
 
Desenvolver um sistema que:
 
- Otimize a reserva e o uso de espaços educacionais.
- Visualize o status das salas e labs.
- Assegure o controle de acesso com autenticação e códigos exclusivos.
 
---
 
## 👥 Atores do Sistema
 
### **Alunos**
- Consultar horários.
- Faz autocadastro no sistema.
 
### **Professores**
- Consultar e reservar horários.
- Visualizar histórico de reservas.
- Visualiza modificações feitas na grade de horários.
 
### **Administradores**
- Cadastrar usuários, disciplinas, salas e laboratórios.
- Configurar regras de reserva e horários de funcionamento.
- Gerar relatórios detalhados e realizar backups.
 
---
 
## 🚀 Funcionalidades Principais
 
1. **Reservas de Espaços**
   - Verificação automática de disponibilidade.
   - Interface intuitiva para reservas.
2. **Notificações Automatizadas**
   - Alertas sobre mudanças ou cancelamentos de reservas.
   - Confirmações e lembretes por e-mail.
3. **Controle de Acesso**
   - Sistema baseado em autenticação e códigos únicos.
 
---
 
## 📑 Restrições
 
- Alunos e professores não podem editar reservas alheias.
- Reservas devem ser feitas com no mínimo **24 horas de antecedência**.

---
 
## 🛠 Tecnologias Utilizadas
 
### **Backend**
- **Linguagem**: Java 17
- **Framework**: Spring Boot (versão 3.2.4)
- **Gerenciamento de Dependências**: Maven
 
### **Frontend**
- **Framework**: Angular 17
 
### **Mobile**
- **Framework**: React Native Expo
 
### **Banco de Dados**
- PostgreSQL, gerenciado com Docker.
 
### **Containerização**
- Docker e Docker Compose para ambientes consistentes.
 
---
 
## 🖥 Diagrama e Modelagem
 
- **Diagramas MER e DER** para modelagem de dados.
- **Casos de uso** detalhando fluxos de trabalho como reservas, consultas e cadastro de usuários.
- **Diagramas UML** para representação gráfica do sistema.
 
---
 
## 🌟 Benefícios Esperados
 
- **Eficiência operacional**: Redução de conflitos e atrasos.
- **Melhoria na comunicação**: Notificações claras e oportunas.
- **Escalabilidade**: Estrutura modular que suporta o crescimento da instituição.
 
---
 
## 📌 Observações
 
- Suporte para dispositivos móveis e navegadores populares.
- Backup diário automático para segurança de dados.
- Integração planejada com sistemas acadêmicos existentes.
 
---
 
