import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciarDisciplinaComponent } from './gerenciar-disciplina.component';

describe('GerenciarDisciplinaComponent', () => {
  let component: GerenciarDisciplinaComponent;
  let fixture: ComponentFixture<GerenciarDisciplinaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GerenciarDisciplinaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GerenciarDisciplinaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
