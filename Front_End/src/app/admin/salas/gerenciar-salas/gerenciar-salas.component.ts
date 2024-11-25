import { Component, OnInit } from '@angular/core';
import { CsalasService } from '../../../serv/admin/csalas.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-gerenciar-salas',
  templateUrl: './gerenciar-salas.component.html',
  styleUrls: ['./gerenciar-salas.component.css']
})
export class GerenciarSalasComponent implements OnInit {
  sala: any[] = [];
  salasFiltradas: any[] = [];
  salasPaginadas: any[] = [];
  pesquisaTipo: string = '';
  loading: boolean = false;
  paginaAtual: number = 1;
  totalPaginas: number = 1;
  itensPorPagina: number = 8;
  paginas: number[] = [];

  constructor(private csalasService: CsalasService,  private titleService: Title) { }

  ngOnInit(): void {
    this.carregarSalas();
    this.titleService.setTitle('Gerenciamento de Salas');

  }

  carregarSalas() {
    this.csalasService.getSalas().subscribe(
      data => {
        this.sala = data;
        this.salasFiltradas = this.sala;
        this.calcularPaginas();
        this.atualizarSalasPaginadas();
      },
      error => {
        console.error('Erro ao carregar salas: ', error);
      }
    );
  }

  calcularPaginas() {
    this.totalPaginas = Math.ceil(this.salasFiltradas.length / this.itensPorPagina);
    this.paginas = Array.from({ length: this.totalPaginas }, (_, i) => i + 1);
  }

  atualizarSalasPaginadas() {
    const inicio = (this.paginaAtual - 1) * this.itensPorPagina;
    const fim = inicio + this.itensPorPagina;
    this.salasPaginadas = this.salasFiltradas.slice(inicio, fim);
  }

  irParaPagina(pagina: number) {
    if (pagina >= 1 && pagina <= this.totalPaginas) {
      this.paginaAtual = pagina;
      this.atualizarSalasPaginadas();
    }
  }

  // Função de filtragem por tipo
  filtrarSalas() {
    this.salasFiltradas = this.sala.filter(sala =>
      sala.roomType.toString().toLowerCase().includes(this.pesquisaTipo.toLowerCase())
    );
    this.paginaAtual = 1;
    this.calcularPaginas();
    this.atualizarSalasPaginadas();
  }

  excluirSalas(roomId: number) {
    if (confirm('Tem certeza que deseja excluir esta sala?')) {
      this.loading = true;
      this.csalasService.deleteSala(roomId).subscribe(
        () => {
          // Atualiza a lista de salas após exclusão
          this.sala = this.sala.filter(sala => sala.roomId !== roomId);
          this.filtrarSalas();
          this.loading = false;
          alert('Sala excluída com sucesso!');
        },
        error => {
          console.error('Erro ao excluir sala: ', error);
          this.loading = false;
          alert('Ocorreu um erro ao excluir a sala.');
        }
      );
    }
  }

}
