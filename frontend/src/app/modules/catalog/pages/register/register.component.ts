import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { EnumNamePipe } from '../../../../shared/pipes/enum-name.pipe';
import { UserService } from '../../core/services/user.service';
import { UserRegister } from '../../shared/models/user-details';
import { Gender } from '../../shared/enums/gender.enum';
import { Router } from '@angular/router';
import { ToastService } from '../../core/services/toast.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslateModule, EnumNamePipe],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  genderList = Object.values(Gender)
  registrationForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private toastService: ToastService,
    private location: Location,
    private router: Router
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

  onSubmit() {
    if (this.registrationForm.valid) {
      this.userService.createNewUser(this.registrationForm.value as UserRegister).subscribe({
        next: () => {
          this.goBack();
          this.toastService.showSuccess('CAT.TOAST.REGISTER.SUCCESS');
        },
        error: e => {
          if (e.error.statusCode == 409) {
            this.toastService.showError(e.error.message)
          } else {
            this.toastService.showError('Nie udało się utworzyć nowego użytkownika.')
          }
        }
      });
    }
  }

  goBack() {
    this.location.back();
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
