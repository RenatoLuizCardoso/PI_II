import { Component, OnInit } from '@angular/core';
import { ChoraService } from '../../serv/admin/chora.service';


@Component({
  selector: 'app-cadastrar-hora',
  templateUrl: './cadastrar-hora.component.html',
  styleUrls: ['./cadastrar-hora.component.css'],
})
export class CadastrarHoraComponent implements OnInit {
  times: any[] = [];
  newStartTime: string = ''; 
  newEndTime: string = ''; 

  constructor(private choraService: ChoraService) {}

  ngOnInit(): void {
    this.loadTimes();
  }

 
  loadTimes(): void {
    this.choraService.getTimes().subscribe({
      next: (response) => {
        this.times = response;
      },
      error: (err) => {
        console.error('Erro ao carregar horários:', err);
      },
    });
  }

  deleteTime(timeId: number): void {
    if (confirm('Tem certeza que deseja excluir este horário?')) {
      this.choraService.deleteTime(timeId).subscribe({
        next: () => {
          this.times = this.times.filter((time) => time.timeId !== timeId);
          console.log(`Horário com ID ${timeId} excluído.`);
          alert('Horário excluído com sucesso!');
        },
        error: (error) => {
          console.error(`Erro ao excluir horário com ID ${timeId}:`, error);
          alert(`Erro ao excluir horário: ${error.message}`);
        },
      });
    }
  }

    addTime(): void {
    if (this.newStartTime && this.newEndTime) {
      const newTime = {
        startTime: this.newStartTime,
        endTime: this.newEndTime,
      };

      this.choraService.addTime(newTime).subscribe({
        next: (response) => {
          this.times.push(response);
          this.newStartTime = '';
          this.newEndTime = '';
        },
        error: (err) => {
          console.error('Erro ao adicionar horário:', err);
        },
      });
    }
  }
}
