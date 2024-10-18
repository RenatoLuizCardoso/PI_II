import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../serv/admin/reservation.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-reservas',
  templateUrl: './reservas.component.html',
  styleUrls: ['./reservas.component.css']
})
export class ReservasComponent implements OnInit {
  searchTerm: string = '';
  selectedShift: string = ''; // Adiciona a variável para filtro de turno
  reservations: any[] = [];
  selectedReservation: any = null; // Adiciona variável para armazenar a reserva selecionada
  showConfirmDialog: boolean = false; // Variável para controlar a exibição do diálogo de confirmação
  reservationToDelete: any = null; // Reserva que está para ser excluída

  constructor(private reservationService: ReservationService) { }

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.reservationService.getReservations().subscribe(
      data => {
        this.reservations = data;
      },
      error => {
        console.error('Erro ao carregar as reservas:', error);
      }
    );
  }

  get filteredReservations() {
    return this.reservations.filter(reservation =>
      reservation.name.toLowerCase().includes(this.searchTerm.toLowerCase()) &&
      (this.selectedShift ? reservation.shift.toLowerCase() === this.selectedShift.toLowerCase() : true)
    );
  }


  moveReservationToRight(reservation: any): void {
    this.selectedReservation = reservation;
  }

  editReservation(): void {
    // Lógica para editar a reserva (por enquanto apenas um log)
    console.log('Editar reserva', this.selectedReservation);
  }

  confirmDelete(): void {
    this.showConfirmDialog = true;
    this.reservationToDelete = this.selectedReservation;
  }

  cancelDelete(): void {
    this.showConfirmDialog = false;
    this.reservationToDelete = null;
  }

  deleteReservation(): void {
    if (this.reservationToDelete) {
      this.reservationService.deleteReservation(this.reservationToDelete.id).subscribe(
        () => {
          this.reservations = this.reservations.filter(reservation => reservation.id !== this.reservationToDelete.id);
          this.selectedReservation = null; // Limpa a reserva selecionada
          this.showConfirmDialog = false;
          this.reservationToDelete = null;
        },
        error => {
          console.error('Erro ao excluir a reserva:', error);
        }
      );
    }
  }
}
