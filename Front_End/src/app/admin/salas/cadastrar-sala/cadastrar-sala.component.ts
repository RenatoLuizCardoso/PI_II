import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { CsalasService } from '../../../serv/admin/csalas.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-cadastrar-sala',
  templateUrl: './cadastrar-sala.component.html',
  styleUrls: ['./cadastrar-sala.component.css']
})
export class CadastrarSalaComponent implements OnInit {
  registerForm!: FormGroup;
  registerError: boolean = false;
  registerSuccess: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private csalasService: CsalasService,
    private titleService: Title
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Cadastro de Salas');
    this.registerForm = this.formBuilder.group({
      roomType: ['', Validators.required], // Tipo da sala (1 ou 2)
      roomCapacity: ['', [Validators.required, Validators.min(1)]], // Capacidade da sala (mínimo 1)
      roomNumber: ['', Validators.required], // Número da sala (obrigatório)
      roomFloor: ['', [Validators.required, Validators.min(0)]], // Andar (mínimo 0)
      roomResources: this.formBuilder.array([]), // Recursos (checkboxes)
      roomAvailability: ['', Validators.required] // Disponibilidade da sala
    });
  }

  get f() { return this.registerForm.controls; }

  get resources(): FormArray {
    return this.registerForm.get('roomResources') as FormArray;
  }

  // Função para adicionar ou remover recursos
  onResourceChange(event: any, resource: string): void {
    const checked = event.target.checked;
    if (checked) {
      this.resources.push(this.formBuilder.control(resource));
    } else {
      const index = this.resources.controls.findIndex(x => x.value === resource);
      if (index !== -1) {
        this.resources.removeAt(index);
      }
    }
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerError = true;
      return;
    }

    // Formatação dos dados para o novo formato esperado pela API
    const formData = {
      roomCapacity: this.registerForm.value.roomCapacity.toString(), // Capacidade da sala (convertido para string)
      roomNumber: this.registerForm.value.roomNumber.padStart(2, '0'), // Garante que o número da sala tenha 2 dígitos
      roomFloor: this.registerForm.value.roomFloor.toString(), // Andar da sala (convertido para string)
      roomResources: this.resources.controls.map((control: any) => control.value).join(', '), // Recursos como string
      roomAvailability: this.registerForm.value.roomAvailability === 'Livre' ? 'Livre' : 'Indisponível',
      roomType: {
        roomTypeId: this.registerForm.value.roomType // Tipo da sala com chave 'roomTypeId'
      }
    };

    // **Log para verificar os dados antes de enviar**
    console.log('Dados do formulário:', JSON.stringify(formData));

    // Envio da solicitação para a API
    this.csalasService.registerSala(formData).subscribe(
      response => {
        console.log('Formulário enviado com sucesso', response);
        this.registerSuccess = true;
        this.registerForm.reset();
        this.registerError = false;
        setTimeout(() => this.registerSuccess = false, 5000);
      },
      error => {
        console.error('Erro ao enviar o formulário', error);
        this.registerError = true;
      }
    );
  }

  // Função para resetar o formulário
  onReset(): void {
    this.registerForm.reset();
    this.registerError = false;
    this.registerSuccess = false;
    this.resources.clear(); // Limpa os recursos selecionados
  }
}
