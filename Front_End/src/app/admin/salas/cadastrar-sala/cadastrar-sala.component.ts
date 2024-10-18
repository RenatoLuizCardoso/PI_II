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
      type: ['', Validators.required],
      capacity: ['', [Validators.required, Validators.min(1)]], // Capacidade deve ser um número positivo
      floor: ['', [Validators.required, Validators.min(0)]], // Andar deve ser um número não-negativo
      resources: this.formBuilder.array([]),
      availability: ['', Validators.required]
    });
  }

  get f() { return this.registerForm.controls; }

  get resources(): FormArray {
    return this.registerForm.get('resources') as FormArray;
  }

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

    this.csalasService.registerSala(this.registerForm.value).subscribe(
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

  onReset(): void {
    this.registerForm.reset();
    this.registerError = false;
    this.registerSuccess = false;
  }
}
