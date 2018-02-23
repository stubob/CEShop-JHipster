/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AddressBookComponent } from '../../../../../../main/webapp/app/entities/address-book/address-book.component';
import { AddressBookService } from '../../../../../../main/webapp/app/entities/address-book/address-book.service';
import { AddressBook } from '../../../../../../main/webapp/app/entities/address-book/address-book.model';

describe('Component Tests', () => {

    describe('AddressBook Management Component', () => {
        let comp: AddressBookComponent;
        let fixture: ComponentFixture<AddressBookComponent>;
        let service: AddressBookService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AddressBookComponent],
                providers: [
                    AddressBookService
                ]
            })
            .overrideTemplate(AddressBookComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressBookComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressBookService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AddressBook(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.addressBooks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
