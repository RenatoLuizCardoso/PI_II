import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Componentes de Login
import { TelaLoginComponent } from './tela-login/tela-login.component';
import { TelaLoginAdmComponent } from './tela-login-adm/tela-login-adm.component';

// Componentes Administrativos
import { TelaInicialAdmComponent } from './tela-inicial-adm/tela-inicial-adm.component';
import { CadastrarProfComponent } from './admin/professor/cadastrar-prof/cadastrar-prof.component';
import { CadastrarSalaComponent } from './admin/salas/cadastrar-sala/cadastrar-sala.component';
import { CadastrarDisciplinasComponent } from './admin/disciplinas/cadastrar-disciplinas/cadastrar-disciplinas.component';
import { GerenciarProfessorComponent } from './admin/professor/gerenciar-professor/gerenciar-professor.component';
import { GerenciarDisciplinaComponent } from './admin/disciplinas/gerenciar-disciplina/gerenciar-disciplina.component';
import { GerenciarCursosComponent } from './admin/cursos/gerenciar-cursos/gerenciar-cursos.component';
import { GerenciarSalasComponent } from './admin/salas/gerenciar-salas/gerenciar-salas.component';
import { EditarDisciplinasComponent } from './admin/disciplinas/editar-disciplinas/editar-disciplinas.component';
import { EditarProfessorComponent } from './admin/professor/editar-professor/editar-professor.component';
import { EditarSalasComponent } from './admin/salas/editar-salas/editar-salas.component';
import { ReservasComponent } from './admin/reservas/reservas.component';
import { CadastrarCursoComponent } from './admin/cursos/cadastrar-curso/cadastrar-curso.component';
import { EditarCursoComponent } from './admin/cursos/editar-curso/editar-curso.component';

// Componentes do Professor
import { TelaInicialProfComponent } from './prof/tela-inicial-prof/tela-inicial-prof.component';
import { PerfilComponent } from './prof/perfil/perfil.component';
import { VisualizarDisciplinaComponent } from './prof/visualizar-disciplina/visualizar-disciplina.component';

// Guardas de Roteamento
import { AdminAuthGuard } from './admin-auth.guard';
import { TeacherAuthGuard } from './teacher-auth.guard';
import { GradeHorarioComponent } from './admin/grade-horario/grade-horario.component';
import { TelaPerfilProfessorComponent } from './prof/tela-perfil-professor/tela-perfil-professor.component';
import { GradeFixaComponent } from './admin/grade-fixa/grade-fixa.component';
import { InicioComponent } from './inicio/inicio.component';

const routes: Routes = [
  // Rotas de Login
  { path: '', component: InicioComponent },
  { path: 'login', component: TelaLoginComponent },
  { path: 'login_adm', component: TelaLoginAdmComponent },

  // Rotas do Administrador
  {
    path: 'admin',
    component: TelaInicialAdmComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/cadastrar_professor',
    component: CadastrarProfComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/cadastrar_sala',
    component: CadastrarSalaComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/cadastrar_disc',
    component: CadastrarDisciplinasComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/gerenciar_disc',
    component: GerenciarDisciplinaComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/cadastrar_curso',
    component: CadastrarCursoComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/gerenciar_professor',
    component: GerenciarProfessorComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/gerenciar_curso',
    component: GerenciarCursosComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/gerenciar_sala',
    component: GerenciarSalasComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/editar_disc/:id',
    component: EditarDisciplinasComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/editar_professores/:id',
    component: EditarProfessorComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/editar_curso/:id',
    component: EditarCursoComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/editar_sala/:id',
    component: EditarSalasComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/gerenciar_reservas',
    component: ReservasComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/teste',
    component: GradeHorarioComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },
  {
    path: 'admin/grade-fixa',
    component: GradeFixaComponent,
    canActivate: [AdminAuthGuard] // Protege com o AdminAuthGuard
  },

  // Rotas do Professor
  {
    path: 'professor',
    component: TelaInicialProfComponent,
    canActivate: [TeacherAuthGuard] // Protege com o TeacherAuthGuard
  },
  {
    path: 'professor/perfil',
    component: PerfilComponent,
    canActivate: [TeacherAuthGuard] // Protege com o TeacherAuthGuard
  },
  {
    path: 'professor/visualizar-disc',
    component: VisualizarDisciplinaComponent,
    canActivate: [TeacherAuthGuard] // Protege com o TeacherAuthGuard
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
