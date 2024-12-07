import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GhorarioService } from '../../serv/admin/ghorario.service';

@Component({
  selector: 'app-cadastro-horario-fixo',
  templateUrl: './cadastro-horario-fixo.component.html',
  styleUrls: ['./cadastro-horario-fixo.component.css']
})
export class CadastroHorarioFixoComponent implements OnInit {
  cadastroForm: FormGroup;
  teachers: any[] = [];
  subjects: any[] = [];
  times: any[] = [];
  rooms: any[] = [];
  courses: any[] = [];
  notificationMessage: string = '';  // Mensagem da notificação
  showNotification: boolean = false;  // Controla se a notificação será exibida

  constructor(private fb: FormBuilder, private ghorarioService: GhorarioService) {
    this.cadastroForm = this.fb.group({
      teacher: [null, Validators.required],
      subject: [null, Validators.required],
      time: [null, Validators.required],
      room: [null, Validators.required],
      course: [null, Validators.required],
      weekDay: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadTeachers();
    this.loadSubjects();
    this.loadTimes();
    this.loadRooms();
    this.loadCourses();
  }

  loadTeachers(): void {
    this.ghorarioService.getTeachers().subscribe({
      next: (data) => {
        this.teachers = data;
      },
      error: (err) => {
        console.error('Erro ao carregar professores:', err);
      }
    });
  }

  loadSubjects(): void {
    this.ghorarioService.getSubjects().subscribe({
      next: (data) => {
        this.subjects = data;
      },
      error: (err) => {
        console.error('Erro ao carregar disciplinas:', err);
      }
    });
  }

  loadTimes(): void {
    this.ghorarioService.getTimes().subscribe({
      next: (data) => {
        this.times = data;
      },
      error: (err) => {
        console.error('Erro ao carregar horários:', err);
      }
    });
  }

  loadRooms(): void {
    this.ghorarioService.getRooms().subscribe({
      next: (data) => {
        this.rooms = data;
      },
      error: (err) => {
        console.error('Erro ao carregar salas:', err);
      }
    });
  }

  loadCourses(): void {
    this.ghorarioService.getCourses().subscribe({
      next: (data) => {
        this.courses = data;
      },
      error: (err) => {
        console.error('Erro ao carregar cursos:', err);
      }
    });
  }

  // Método onSubmit para enviar os dados
  onSubmit(): void {
    if (this.cadastroForm.invalid) {
      this.showNotification = true;
      this.notificationMessage = 'Por favor, preencha todos os campos obrigatórios!';
      setTimeout(() => this.showNotification = false, 3000);  // Ocultar a notificação após 3 segundos
      return;
    }

    const formData = {
      teacher: {
        teacherId: this.cadastroForm.value.teacher
      },
      subject: {
        subjectId: this.cadastroForm.value.subject
      },
      time: {
        timeId: this.cadastroForm.value.time
      },
      room: {
        roomId: this.cadastroForm.value.room
      },
      course: {
        courseId: this.cadastroForm.value.course
      },
      weekDay: this.cadastroForm.value.weekDay
    };

    console.log('Formulário Enviado:', formData);

    // Enviar os dados para a API
    this.ghorarioService.createSchedule(formData).subscribe({
      next: (response) => {
        console.log('Agendamento criado com sucesso:', response);
        this.showNotification = true;
        this.notificationMessage = 'Horário cadastrado com sucesso!';
        setTimeout(() => this.showNotification = false, 3000);  // Ocultar a notificação após 3 segundos
      },
      error: (err) => {
        console.error('Erro ao criar agendamento:', err);
        this.showNotification = true;
        this.notificationMessage = 'Erro ao cadastrar horário! Tente novamente.';
        setTimeout(() => this.showNotification = false, 3000);  // Ocultar a notificação após 3 segundos
      }
    });
  }
}
