import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizarDisciplinaComponent } from './visualizar-disciplina.component';

describe('VisualizarDisciplinaComponent', () => {
  let component: VisualizarDisciplinaComponent;
  let fixture: ComponentFixture<VisualizarDisciplinaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VisualizarDisciplinaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VisualizarDisciplinaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
