import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AddressBookComponent } from './address-book.component';
import { AddressBookDetailComponent } from './address-book-detail.component';
import { AddressBookPopupComponent } from './address-book-dialog.component';
import { AddressBookDeletePopupComponent } from './address-book-delete-dialog.component';

export const addressBookRoute: Routes = [
    {
        path: 'address-book',
        component: AddressBookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressBooks'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'address-book/:id',
        component: AddressBookDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressBooks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const addressBookPopupRoute: Routes = [
    {
        path: 'address-book-new',
        component: AddressBookPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressBooks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-book/:id/edit',
        component: AddressBookPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressBooks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-book/:id/delete',
        component: AddressBookDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressBooks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
