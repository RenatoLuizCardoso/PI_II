import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { CsalasService } from '../../../serv/admin/csalas.service';

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
    private csalasService: CsalasService
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      roomType: ['', Validators.required], // Tipo da sala (1 ou 2)
      roomCapacity: ['', [Validators.required, Validators.min(1)]], // Capacidade da sala (mínimo 1)
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

  // Envio do formulário com a estrutura de dados adequada para o novo backend
  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerError = true;
      return;
    }

    // Certificando-se de que roomType seja um número (1 ou 2)
    let roomType = this.registerForm.value.roomType;
    roomType = Number(roomType); // Converte roomType para número (1 ou 2)

    // Verificação se roomType está correto
    if (![1, 2].includes(roomType)) {
      console.error('roomType deve ser 1 ou 2');
      this.registerError = true;
      return;
    }

    // Formatação dos dados para o novo formato esperado pela API
    const formData = {
      roomCapacity: this.registerForm.value.roomCapacity, // Capacidade da sala
      roomFloor: this.registerForm.value.roomFloor, // Andar da sala
      roomResources: this.resources.controls.map((control: any) => control.value).join(', '), // Recursos como string
      roomAvailability: this.registerForm.value.roomAvailability, // Disponibilidade
      roomType: roomType // Tipo da sala (agora garantido como número 1 ou 2)
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
