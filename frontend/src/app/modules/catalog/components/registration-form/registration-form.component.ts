import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { UserRegister } from '../../shared/models/user-details';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../core/services/user.service';
import { TranslateModule } from '@ngx-translate/core';
import { EnumNamePipe } from "../../../../shared/pipes/enum-name.pipe";
import { Gender } from '../../shared/enums/gender.enum';

@Component({
  selector: 'app-registration-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslateModule, EnumNamePipe],
  templateUrl: './registration-form.component.html',
  styleUrl: './registration-form.component.css'
})
export class RegistrationFormComponent implements AfterViewInit {
  genderList = Object.values(Gender)
  registrationForm!: FormGroup;
  @ViewChild('registrationModal') registrationModal!: ElementRef;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        this.specialCharacterValidator(),
        this.lowerCaseValidator(),
        this.upperCaseValidator(),
        this.digitValidator()
      ]],
      confirmPassword: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      pesel: ['', Validators.required],
      gender: ['', Validators.required],
      nationality: ['', Validators.required],
      mothersName: ['', Validators.required],
      fathersName: ['', Validators.required],
      streetAddress: ['', Validators.required],
      zipCode: ['', Validators.required],
      city: ['', Validators.required],
      state: [''],
      country: ['', Validators.required],
      phone: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  ngAfterViewInit(): void {
    const modalElement = this.registrationModal.nativeElement;
    modalElement.addEventListener('hidden.bs.modal', () => {
      this.registrationForm.reset();
    });
  }

  onSubmit() {
    if (this.registrationForm.valid) {
      this.userService.createNewUser(this.registrationForm.value as UserRegister);
    }
  }

  private specialCharacterValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value || '';
      const hasSpecialCharacter = /[!@#$%^&*(),.?":{}|<>]/.test(value);
      return hasSpecialCharacter ? null : { specialCharacter: true };
    };
  }
  private lowerCaseValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value || '';
      const hasLowerCase = /[a-z]/.test(value)
      return hasLowerCase ? null : { lowerCase: true };
    };
  }
  private upperCaseValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value || '';
      const hasUpperCase = /[A-Z]/.test(value)
      return hasUpperCase ? null : { upperCase: true };
    };
  }
  private digitValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value || '';
      const hasDigit = /\d/.test(value);
      return hasDigit ? null : { digit: true };
    };
  }
  

  private passwordMatchValidator(group: AbstractControl): ValidationErrors | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }
}
