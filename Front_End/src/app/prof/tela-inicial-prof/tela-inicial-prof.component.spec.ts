import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaInicialProfComponent } from './tela-inicial-prof.component';

describe('TelaInicialProfComponent', () => {
  let component: TelaInicialProfComponent;
  let fixture: ComponentFixture<TelaInicialProfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TelaInicialProfComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaInicialProfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
