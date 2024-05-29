import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login.component';
import { RegistroComponent } from './auth/registro.component';
import { DetallesproductoComponent } from './Componentes/producto/detallesproducto.component';
import { EditarproductoComponent } from './Componentes/producto/editarproducto.component';
import { NuevoproductoComponent } from './Componentes/producto/nuevoproducto.component';
import { ProductoComponent } from './Componentes/producto/producto.component';
import { ProdGuardService as guard } from './guards/prod-guard.service';
import { RenovarComponent } from './auth/renovar.component';
import { LoginGuard } from './guards/login.guard';
import { NuevoUsuario } from './Modelo/nuevo-usuario';
import { NuevouserComponent} from './Componentes/producto/nuevouser.component';
import { UsuarioComponent } from './Componentes/producto/users.component';
import { PerfiluserComponent } from './Componentes/producto/perfiluser.component';



const routes: Routes = [
  {path: 'login', component: LoginComponent, canActivate: [LoginGuard]},
  {path: 'registro', component: RegistroComponent, canActivate:[LoginGuard]},
  {path: 'renovar', component: RenovarComponent, canActivate:[LoginGuard]},
  {path: 'lista', component: ProductoComponent,canActivate: [guard], data: {expectedRol: ['admin','user']}},
  {path: 'editarproducto/:id', component: EditarproductoComponent, canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'nuevoproducto', component: NuevoproductoComponent, canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'detallesproducto/:id', component: DetallesproductoComponent, canActivate: [guard], data: {expectedRol: ['admin', 'user']}},
  {path: 'nuevouser', component: NuevouserComponent, canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'usuarios', component: UsuarioComponent, canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'perfil', component: PerfiluserComponent, canActivate: [guard], data: {expectedRol: ['admin', 'user']}},
  {path: '', redirectTo:'login', pathMatch:'full'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
],
  exports: [RouterModule]
})
export class AppRoutingModule { }
