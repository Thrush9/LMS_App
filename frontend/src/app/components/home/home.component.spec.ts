import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HomeComponent } from './home.component';
import { By } from '@angular/platform-browser';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [RouterTestingModule, HttpClientTestingModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should have Hero-image element', () => {
    let imageObj = fixture.debugElement.query(By.css('.hero-image'));
    expect(imageObj).toBeTruthy();
  });

  it('should have Hero-image-btn element', () => {
    let textObj = fixture.debugElement.query(By.css('.hero-text .hero-img-btn'));
    expect(textObj).toBeTruthy();
    expect(textObj.nativeElement.textContent).toEqual('Explore Now ');
  });

  it('should have info-cards element', () => {
    let textObj = fixture.debugElement.query(By.css('.info-cards'));
    expect(textObj).toBeTruthy();
  });

});
