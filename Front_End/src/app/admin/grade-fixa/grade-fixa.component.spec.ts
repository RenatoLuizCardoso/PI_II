import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeFixaComponent } from './grade-fixa.component';

describe('GradeFixaComponent', () => {
  let component: GradeFixaComponent;
  let fixture: ComponentFixture<GradeFixaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GradeFixaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GradeFixaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
