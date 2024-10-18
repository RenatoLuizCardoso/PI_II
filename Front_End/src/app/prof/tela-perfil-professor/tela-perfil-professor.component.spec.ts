import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaPerfilProfessorComponent } from './tela-perfil-professor.component';

describe('TelaPerfilProfessorComponent', () => {
  let component: TelaPerfilProfessorComponent;
  let fixture: ComponentFixture<TelaPerfilProfessorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TelaPerfilProfessorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaPerfilProfessorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
