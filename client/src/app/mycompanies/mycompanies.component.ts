import { Component, OnInit } from '@angular/core';

import { Company } from '../_models/company.model';
import { CompanyService } from '../_services/company.service';
import { MockAuthService } from '../_services';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
    moduleId: module.id,
    templateUrl: 'mycompanies.component.html'
})

export class MyCompaniesComponent implements OnInit {

    companies$: Observable<Company[]>;
    selectedRequest: Company;
    companiesCreationDates = new Map<number, Date>();

    test: Observable<Date>;

    constructor(private companyService: CompanyService, private authService: AuthenticationService) {}

    ngOnInit() {
        const currentUser = this.authService.getCurrentUser();
        this.companies$ = this.companyService.getCompany(currentUser.id);
        this.companies$.subscribe(requests => {
            for (const request of requests) {
                this.companyService.getCreationDate(request.id)
                        .subscribe(creationDate => this.companiesCreationDates[request.id] = creationDate);
            }
        });
    }
}
