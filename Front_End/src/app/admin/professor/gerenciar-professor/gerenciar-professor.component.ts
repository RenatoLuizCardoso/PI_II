import { ProfessoresService } from '../../../serv/admin/professores.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-gerenciar-professor',
  templateUrl: './gerenciar-professor.component.html',
  styleUrls: ['./gerenciar-professor.component.css']
})
export class GerenciarProfessorComponent implements OnInit {
  professores: any[] = [];
  professoresFiltrados: any[] = []; // Professores filtrados após a pesquisa
  professoresPaginados: any[] = []; // Professores exibidos na página atual
  pesquisaNome: string = ''; // Valor do campo de pesquisa
  loading: boolean = false;
  paginaAtual: number = 1;
  totalPaginas: number = 1;
  itensPorPagina: number = 8;
  paginas: number[] = [];

  constructor(private professoresService: ProfessoresService) { }

  ngOnInit(): void {
    this.carregarProfessores();
  }

  carregarProfessores() {
    this.professoresService.getProfessores().subscribe(
      data => {
        // Adaptar os dados para a nova estrutura
        this.professores = data.map(professor => ({
          id: professor.teacherId, // Usar teacherId em vez de id
          name: professor.teacherName, // Usar teacherName
          curso: professor.subjects.length > 0 ? professor.subjects[0] : 'Sem curso', // Usar o primeiro item de subjects
          emailI: professor.institutionalEmail, // Usar institutionalEmail
          tel: professor.personalPhone // Usar personalPhone
        }));

        this.professoresFiltrados = this.professores; // Inicialmente, todos os professores estão na lista filtrada
        this.calcularPaginas();
        this.atualizarProfessoresPaginados();
      },
      error => {
        console.error('Erro ao carregar professores: ', error);
      }
    );
  }

  calcularPaginas() {
    this.totalPaginas = Math.ceil(this.professoresFiltrados.length / this.itensPorPagina);
    this.paginas = Array.from({ length: this.totalPaginas }, (_, i) => i + 1);
  }

  atualizarProfessoresPaginados() {
    const inicio = (this.paginaAtual - 1) * this.itensPorPagina;
    const fim = inicio + this.itensPorPagina;
    this.professoresPaginados = this.professoresFiltrados.slice(inicio, fim);
  }

  irParaPagina(pagina: number) {
    if (pagina >= 1 && pagina <= this.totalPaginas) {
      this.paginaAtual = pagina;
      this.atualizarProfessoresPaginados();
    }
  }

  // Função de filtragem por nome
  filtrarProfessores() {
    this.professoresFiltrados = this.professores.filter(professor =>
      professor.name.toLowerCase().includes(this.pesquisaNome.toLowerCase())
    );
    this.paginaAtual = 1; // Resetar para a primeira página ao filtrar
    this.calcularPaginas();
    this.atualizarProfessoresPaginados();
  }

  excluirProfessor(id: number) {
    if (confirm('Tem certeza que deseja excluir este professor?')) {
      this.loading = true;
      this.professoresService.deleteProfessor(id).subscribe(
        () => {
          this.professores = this.professores.filter(professor => professor.id !== id);
          this.filtrarProfessores(); // Reaplica o filtro após a exclusão
          this.loading = false;
          alert('Professor excluído com sucesso!');
        },
        error => {
          console.error('Erro ao excluir professor: ', error);
          this.loading = false;
          alert('Ocorreu um erro ao excluir o professor.');
        }
      );
    }
  }
}
