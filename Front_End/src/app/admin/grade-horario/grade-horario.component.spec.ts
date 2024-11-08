import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeHorarioComponent } from './grade-horario.component';

describe('GradeHorarioComponent', () => {
  let component: GradeHorarioComponent;
  let fixture: ComponentFixture<GradeHorarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GradeHorarioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GradeHorarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
