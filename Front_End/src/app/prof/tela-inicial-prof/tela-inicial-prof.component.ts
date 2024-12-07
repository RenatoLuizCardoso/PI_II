import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-tela-inicial-prof',
  templateUrl: './tela-inicial-prof.component.html',
  styleUrls: ['./tela-inicial-prof.component.css']
})
export class TelaInicialProfComponent implements OnInit {
  saudacao: string = '';  // Definindo a variável de saudação

  constructor(private titleService: Title) {}

  ngOnInit(): void {
    this.titleService.setTitle('Página Inicial - Professor');  // Define o título da aba para o professor
    this.definirSaudacao();  // Chama a função para definir a saudação com base no horário
  }

  // Função para definir a saudação com base na hora do dia
  definirSaudacao(): void {
    const hora = new Date().getHours();
    if (hora >= 0 && hora < 6) {
      this.saudacao = 'Trabalhando tarde da noite, Professor?';
    } else if (hora >= 6 && hora < 12) {
      this.saudacao = 'Bom Dia, Professor';
    } else if (hora >= 12 && hora < 18) {
      this.saudacao = 'Boa Tarde, Professor';
    } else {
      this.saudacao = 'Boa Noite, Professor';
    }
  }
}
