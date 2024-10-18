import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciarProfessorComponent } from './gerenciar-professor.component';

describe('GerenciarProfessorComponent', () => {
  let component: GerenciarProfessorComponent;
  let fixture: ComponentFixture<GerenciarProfessorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GerenciarProfessorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GerenciarProfessorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
