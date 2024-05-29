import { Component, OnInit } from '@angular/core';
import { AuthService } from '../Servicios/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RenovarPass } from '../Modelo/renovar-pass'; 
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CustomValidators } from './custom-validators';

@Component({
  selector: 'app-renovar',
  templateUrl: './renovar.component.html',
  styleUrls: ['./renovar.component.css']
})
export class RenovarComponent implements OnInit{

  isUpdate = false;
  isUpdatefail = false;
  nombre: string;
  nombreUsuario: string;
  email: string;
  password: string;
  confirmacion:string;
  pregunta: string;
  respuesta: string;
  recuperaUsusario: RenovarPass;

  frmSignup: FormGroup;

 constructor( private  authService: AuthService,
  private router: Router, private activatedRouter: ActivatedRoute,private fb: FormBuilder){
    this.frmSignup = this.validador();
  }
 

  ngOnInit(): void {}

  onUpdate(): void{
    
    const password = this.password;
    const confirmacion = this.confirmacion;
      const usuario = new RenovarPass(this.nombre,this.nombreUsuario, this.email, this.password, this.pregunta, this.respuesta);
      console.log(this.nombre,this.nombreUsuario, this.email, this.password, this.pregunta, this.respuesta);
      if (this.password === confirmacion) {

      this.authService.renovar(usuario).subscribe(
      data => { 
        this.isUpdate = true; //Se quita con el toastr
        this.isUpdatefail = false; 
        alert("Modificación correcta");
        this.router.navigate(['/login']);
      }, err => {
        this.isUpdate = false; //Se quita con el toastr
        this.isUpdatefail = true; //Se quita con el toastr
        alert("No se logro cambiar la contraseña");
      }
    ); 
      }
      else {
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
