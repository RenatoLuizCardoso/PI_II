import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaLoginAdmComponent } from './tela-login-adm.component';

describe('TelaLoginAdmComponent', () => {
  let component: TelaLoginAdmComponent;
  let fixture: ComponentFixture<TelaLoginAdmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TelaLoginAdmComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaLoginAdmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
