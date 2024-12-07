import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatCardModule } from '@angular/material/card';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavbarComponent } from './navbar/navbar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TelaInicialProfComponent } from './prof/tela-inicial-prof/tela-inicial-prof.component';
import { AuthpService } from './authp.service';
import { TelaInicialAdmComponent } from './tela-inicial-adm/tela-inicial-adm.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { TelaLoginComponent } from './tela-login/tela-login.component';
import { TelaLoginAdmComponent } from './tela-login-adm/tela-login-adm.component';

import { CadastrarSalaComponent } from './admin/salas/cadastrar-sala/cadastrar-sala.component';
import { CadastrarDisciplinasComponent } from './admin/disciplinas/cadastrar-disciplinas/cadastrar-disciplinas.component';
import { CadastrarCursoComponent } from './admin/cursos/cadastrar-curso/cadastrar-curso.component';
import { GerenciarProfessorComponent } from './admin/professor/gerenciar-professor/gerenciar-professor.component';
import { GerenciarDisciplinaComponent } from './admin/disciplinas/gerenciar-disciplina/gerenciar-disciplina.component';
import { GerenciarSalasComponent } from './admin/salas/gerenciar-salas/gerenciar-salas.component';
import { GerenciarCursosComponent } from './admin/cursos/gerenciar-cursos/gerenciar-cursos.component';
import { EditarDisciplinasComponent } from './admin/disciplinas/editar-disciplinas/editar-disciplinas.component';
import { EditarProfessorComponent } from './admin/professor/editar-professor/editar-professor.component';
import { EditarCursoComponent } from './admin/cursos/editar-curso/editar-curso.component';
import { EditarSalasComponent } from './admin/salas/editar-salas/editar-salas.component';
import { ReservasComponent } from './admin/reservas/reservas.component';
import { PerfilComponent } from './prof/perfil/perfil.component';
import { NavbarProfComponent } from './navbar-prof/navbar-prof.component';
import { TelaPerfilProfessorComponent } from './prof/tela-perfil-professor/tela-perfil-professor.component';
import { VisualizarDisciplinaComponent } from './prof/visualizar-disciplina/visualizar-disciplina.component';
import { HttpClientModule } from '@angular/common/http';
import { CadastrarProfComponent } from './admin/professor/cadastrar-prof/cadastrar-prof.component';
import { GradeHorarioComponent } from './admin/grade-horario/grade-horario.component';
import { GradeFixaComponent } from './admin/grade-fixa/grade-fixa.component';
import { InicioComponent } from './inicio/inicio.component';
import { SeminavbarComponent } from './seminavbar/seminavbar.component';
import { TimeFilterPipe } from './pipes/time-filter.pipe';
import { DayFilterPipe } from './pipes/day-filter.pipe';
import { CadastroHorarioFixoComponent } from './admin/cadastro-horario-fixo/cadastro-horario-fixo.component';
import { GradeFixaProfComponent } from './prof/grade-fixa-prof/grade-fixa-prof.component';
import { ReservarComponent } from './prof/reservar/reservar.component';
import { VisualizarReservasComponent } from './prof/visualizar-reservas/visualizar-reservas.component';
import { CadastrarHoraComponent } from './admin/cadastrar-hora/cadastrar-hora.component';








@NgModule({
  declarations: [
    AppComponent,
    TelaLoginComponent,
    NavbarComponent,
    TelaInicialProfComponent,
    TelaInicialAdmComponent,
    TelaLoginAdmComponent,
    CadastrarProfComponent,
    CadastrarSalaComponent,
    CadastrarDisciplinasComponent,
    CadastrarCursoComponent,
    GerenciarProfessorComponent,
    GerenciarDisciplinaComponent,
    GerenciarSalasComponent,
    GerenciarCursosComponent,
    EditarDisciplinasComponent,
    EditarProfessorComponent,
    EditarCursoComponent,
    EditarSalasComponent,
    ReservasComponent,
    PerfilComponent,
    NavbarProfComponent,
    TelaPerfilProfessorComponent,
    VisualizarDisciplinaComponent,
    GradeHorarioComponent,
    GradeFixaComponent,
    InicioComponent,
    SeminavbarComponent,
    TimeFilterPipe,
    DayFilterPipe,
    CadastroHorarioFixoComponent,
    GradeFixaProfComponent,
    ReservarComponent,
    VisualizarReservasComponent,
    CadastrarHoraComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    MatCardModule,
    HttpClientModule,
    FormsModule

  ],
  providers: [AuthpService, provideAnimationsAsync()],
  bootstrap: [AppComponent]
})
export class AppModule { }
