/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AddressBookDetailComponent } from '../../../../../../main/webapp/app/entities/address-book/address-book-detail.component';
import { AddressBookService } from '../../../../../../main/webapp/app/entities/address-book/address-book.service';
import { AddressBook } from '../../../../../../main/webapp/app/entities/address-book/address-book.model';

describe('Component Tests', () => {

    describe('AddressBook Management Detail Component', () => {
        let comp: AddressBookDetailComponent;
        let fixture: ComponentFixture<AddressBookDetailComponent>;
        let service: AddressBookService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AddressBookDetailComponent],
                providers: [
                    AddressBookService
                ]
            })
            .overrideTemplate(AddressBookDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressBookDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressBookService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AddressBook(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.addressBook).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
