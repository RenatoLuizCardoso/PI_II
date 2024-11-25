import { Component, OnInit } from '@angular/core';
import { CdisciplinaService } from '../../../serv/admin/cdisciplina.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-gerenciar-disciplina',
  templateUrl: './gerenciar-disciplina.component.html',
  styleUrls: ['./gerenciar-disciplina.component.css']
})
export class GerenciarDisciplinaComponent implements OnInit {
  disciplinas: any[] = [];
  disciplinasFiltradas: any[] = []; // Disciplinas filtradas após a pesquisa
  disciplinasPaginadas: any[] = []; // Disciplinas exibidas na página atual
  pesquisaNome: string = ''; // Valor do campo de pesquisa
  loading: boolean = false;
  paginaAtual: number = 1;
  totalPaginas: number = 1;
  itensPorPagina: number = 8;
  paginas: number[] = [];

  constructor(private cdisciplinaService: CdisciplinaService, private titleService: Title) { }

  ngOnInit(): void {
    this.carregarDisciplinas();
    this.titleService.setTitle('Gerenciamento de Disciplinas');
  }

  carregarDisciplinas() {
    this.cdisciplinaService.getDisciplines().subscribe(
      data => {
        // Loga o JSON carregado no console
        console.log('JSON carregado:', data);

        // Aqui estamos assumindo que a resposta é um array de objetos com as propriedades 'subjectId', 'subjectName' e 'subjectHours'
        this.disciplinas = data;
        this.disciplinasFiltradas = this.disciplinas; // Inicialmente, todas as disciplinas estão na lista filtrada
        this.calcularPaginas();
        this.atualizarDisciplinasPaginadas();
      },
      error => {
        console.error('Erro ao carregar disciplinas: ', error);
      }
    );
  }

  calcularPaginas() {
    this.totalPaginas = Math.ceil(this.disciplinasFiltradas.length / this.itensPorPagina);
    this.paginas = Array.from({ length: this.totalPaginas }, (_, i) => i + 1);
  }

  atualizarDisciplinasPaginadas() {
    const inicio = (this.paginaAtual - 1) * this.itensPorPagina;
    const fim = inicio + this.itensPorPagina;
    this.disciplinasPaginadas = this.disciplinasFiltradas.slice(inicio, fim);
  }

  irParaPagina(pagina: number) {
    if (pagina >= 1 && pagina <= this.totalPaginas) {
      this.paginaAtual = pagina;
      this.atualizarDisciplinasPaginadas();
    }
  }

  // Função de filtragem por nome
  filtrarDisciplinas() {
    this.disciplinasFiltradas = this.disciplinas.filter(disciplina =>
      disciplina.subjectName.toLowerCase().includes(this.pesquisaNome.toLowerCase())
    );
    this.paginaAtual = 1; // Resetar para a primeira página ao filtrar
    this.calcularPaginas();
    this.atualizarDisciplinasPaginadas();
  }

  excluirDisciplina(id: number) {
    if (confirm('Tem certeza que deseja excluir esta disciplina?')) {
      this.loading = true;
      this.cdisciplinaService.deleteDiscipline(id).subscribe(
        () => {
          this.disciplinas = this.disciplinas.filter(disciplina => disciplina.subjectId !== id);
          this.filtrarDisciplinas(); // Reaplica o filtro após a exclusão
          this.loading = false;
          alert('Disciplina excluída com sucesso!');
        },
        error => {
          console.error('Erro ao excluir disciplina: ', error);
          this.loading = false;
          alert('Ocorreu um erro ao excluir a disciplina.');
        }
      );
    }
  }
}
