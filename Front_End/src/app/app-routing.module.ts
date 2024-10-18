import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TelaLoginComponent } from './tela-login/tela-login.component';
import { TelaInicialProfComponent } from './prof/tela-inicial-prof/tela-inicial-prof.component';
import { authpGuard } from './guards/authp.guard';
import { TelaInicialAdmComponent } from './tela-inicial-adm/tela-inicial-adm.component';
import { TelaLoginAdmComponent } from './tela-login-adm/tela-login-adm.component';
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
import { PerfilComponent } from './prof/perfil/perfil.component';
import { VisualizarDisciplinaComponent } from './prof/visualizar-disciplina/visualizar-disciplina.component';

const routes: Routes = [
  {path: 'login', component: TelaLoginComponent},
  {path: 'login-adm', component: TelaLoginAdmComponent},
  {path: '', component: TelaInicialAdmComponent},
  {path: 'admin/cadastrar_professor', component: CadastrarProfComponent},
  {path: 'admin/cadastrar_sala', component: CadastrarSalaComponent},
  {path: 'admin/cadastrar_disc', component: CadastrarDisciplinasComponent},
  {path: 'admin/gerenciar_disc', component: GerenciarDisciplinaComponent},
  {path: 'admin/cadastrar_curso', component: CadastrarCursoComponent},
  {path: 'admin/gerenciar_professor', component: GerenciarProfessorComponent},
  {path: 'admin/gerenciar_curso', component: GerenciarCursosComponent},
  {path: 'admin/gerenciar_sala', component: GerenciarSalasComponent},
  {path: 'admin/editar_disc/:id', component: EditarDisciplinasComponent},
  {path: 'admin/editar_professores/:id', component: EditarProfessorComponent},
  {path: 'admin/editar_curso/:id', component: EditarCursoComponent},
  {path: 'admin/editar_sala/:id', component: EditarSalasComponent},
  {path: 'admin/gerenciar_reservas', component: ReservasComponent},
  {path: 'home', component: TelaInicialProfComponent},
  {path: 'perfil', component: PerfilComponent},
  {path:'prof/visualizar-Disc', component: VisualizarDisciplinaComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

 }
