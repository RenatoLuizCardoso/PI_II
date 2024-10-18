import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarProfComponent } from './navbar-prof.component';

describe('NavbarProfComponent', () => {
  let component: NavbarProfComponent;
  let fixture: ComponentFixture<NavbarProfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavbarProfComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NavbarProfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
