import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LandingPageGuard } from './landingpage.guard';

describe('LandingPageGuard', () => {
  let guard: LandingPageGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule]
    });
    guard = TestBed.inject(LandingPageGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
