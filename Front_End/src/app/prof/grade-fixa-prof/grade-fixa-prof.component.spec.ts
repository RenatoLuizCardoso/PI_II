import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeFixaProfComponent } from './grade-fixa-prof.component';

describe('GradeFixaProfComponent', () => {
  let component: GradeFixaProfComponent;
  let fixture: ComponentFixture<GradeFixaProfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GradeFixaProfComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GradeFixaProfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
