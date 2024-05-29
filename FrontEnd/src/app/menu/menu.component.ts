import { Component, OnInit } from '@angular/core';
import { TokenService } from '../Servicios/token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit{
  isLogged = false;
  isAdmin = false;
  

  constructor(private tokenservice: TokenService){ }

  ngOnInit(): void {
    this.isLogged = this.tokenservice.isLogged();
    this.isAdmin = this.tokenservice.IsAdmin();
  }

  onLogOut(): void {
    this.tokenservice.logOut();
  }
}
