import { Component, OnDestroy, OnInit } from '@angular/core';
import { NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService } from '@nebular/theme';
import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { AuthService } from '../../../shared/services/auth.service';
import { Router } from '@angular/router';
import { User } from '../../../shared/models/User';

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  user: User;

  currentTheme = 'default';

  userMenu = [{ title: 'Profile' }, { title: 'Log out' }];

  constructor(private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private themeService: NbThemeService,
    private breakpointService: NbMediaBreakpointsService,
    private authService: AuthService,
    private router: Router) {
  }

  ngOnInit() {
    // Get user data
    this.authService.loggedData().then(data => {
      this.user=data;
    }).catch(error => {
      if (error.status==401) {
        this.authService.logOut();
        alert("Your session has expired!");
        this.router.navigate(['/login']);
      }
    });

    // Subscribe to menu service, to handle user clicks
    this.menuService.onItemClick()
      .subscribe(obj => {
        if (obj.item.title == "Log out") {
          this.authService.logOut();
          this.router.navigate(['/login']);
        } else if (obj.item.title == "Profile") {
          var role = this.authService.role();
          if (role == "BUSINESS") {
            this.router.navigate(['/business/profile']);
          } else if (role == "PROVIDER") {
            alert("Page available soon!");
          }
        }
      });

    // Do stuff template already did
    const { xl } = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);

    this.themeService.onThemeChange()
      .pipe(
        map(({ name }) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  changeTheme(themeName: string) {
    this.themeService.changeTheme(themeName);
  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }
}
