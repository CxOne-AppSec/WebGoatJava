import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NuevoUsuario } from 'src/app/Modelo/nuevo-usuario';
import { AuthService } from 'src/app/Servicios/auth.service';
import { CustomValidators } from 'src/app/auth/custom-validators';

@Component({
  selector: 'app-perfiluser',
  templateUrl: './perfiluser.component.html',
  styleUrls: ['./perfiluser.component.css']
})
export class PerfiluserComponent implements OnInit{
  user2:NuevoUsuario;
  password: string;
  nuevaPassword: string;
  confirmacion: string;
  errMsj: string;
  frmSignup: FormGroup;
 
  constructor(private perfilS:AuthService, private router: Router, private activatedRouter: ActivatedRoute, private fb: FormBuilder){ 
    this.user2 = {} as NuevoUsuario; 
    this.frmSignup = this.validador();
  }
  
  ngOnInit(): void {
    this.cargarUsuario();
  }
  cargarUsuario(){
    this.perfilS.perfil().subscribe(
      data => {
        this.user2 = data;
      }
    )
}

onUpdate(): void {
  const password = this.password;
  const nuevaPassword = this.nuevaPassword;
  const confirmacion = this.confirmacion;
  // Obtener la contraseña actual y la nueva contraseña de los input
  if (nuevaPassword === confirmacion) {
    // Si son iguales, actualizar la contraseña
    this.perfilS.nuevapass(password, nuevaPassword).subscribe(
      data => {
        // Actualizar la contraseña y mostrar un mensaje de éxito
        this.nuevaPassword = ''; // Limpiar los campos
        this.confirmacion = '';
        alert('Contraseña actualizada');
        this.router.navigate(['/perfil']);
      },
      err => {
        alert('No se logró actualizar la contraseña');
      }
    );
  } else {
    alert('Las contraseñas no coinciden. Inténtalo de nuevo.');
  }
}


validador(): FormGroup {
  return this.fb.group(
    {
      email: [
        null,
        Validators.compose([Validators.email, Validators.required])
      ],
      password: [
        null,
        Validators.compose([
          Validators.required,
          // check whether the entered password has a number
          CustomValidators.patternValidator(/\d/, {
            hasNumber: true
          }),
          // check whether the entered password has upper case letter
          CustomValidators.patternValidator(/[A-Z]/, {
            hasCapitalCase: true
          }),
          // check whether the entered password has a lower case letter
          CustomValidators.patternValidator(/[a-z]/, {
            hasSmallCase: true
          }),
          // check whether the entered password has a special character
          CustomValidators.patternValidator(
            /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/,
            {
              hasSpecialCharacters: true
            }
          ),
          Validators.minLength(8)
        ])
      ],
      confirmPassword: [null, Validators.compose([Validators.required])]
    },
    {
      // check whether our password and confirm password match
      validator: CustomValidators.passwordMatchValidator
    }
  );
}
}

