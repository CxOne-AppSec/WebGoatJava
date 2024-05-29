import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductoComponent } from './Componentes/producto/producto.component';
import { NuevoproductoComponent } from './Componentes/producto/nuevoproducto.component';
import { EditarproductoComponent } from './Componentes/producto/editarproducto.component';
import { DetallesproductoComponent } from './Componentes/producto/detallesproducto.component';
import { RegistroComponent } from './auth/registro.component';
import { NuevouserComponent } from './Componentes/producto/nuevouser.component';
//external
import { LoginComponent } from './auth/login.component';
import { RenovarComponent } from './auth/renovar.component';
import { MenuComponent } from './menu/menu.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthInterceptor } from './guards/interceptor';
import { UsuarioComponent } from './Componentes/producto/users.component';
import { PerfiluserComponent } from './Componentes/producto/perfiluser.component';

import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    ProductoComponent,
    NuevoproductoComponent,
    EditarproductoComponent,
    DetallesproductoComponent,
    RegistroComponent,
    LoginComponent,
    MenuComponent,
    RenovarComponent,
    NuevouserComponent,
    UsuarioComponent,
    PerfiluserComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,  
    ReactiveFormsModule,
  ],
  bootstrap: [AppComponent],

  providers:[
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,},
  ],
})
export class AppModule { }
