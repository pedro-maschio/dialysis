# dialysis

A simple Peritoneal dialysis tracker written completely in Kotlin using the principles of [modern app architecture](https://developer.android.com/topic/architecture) recommended by Google.

## Features
- Allows user to create and edit dialysis notes;
- Allows users to generate PDFs with the selected target interval locally;
- All data is stored in a Room database

### App created with sole purpose of learning - a lot of issues in the code yet

## What is missing

- [ ] Prepare the app to introduce testing
- [ ] Introduce testing itself
- [ ] Introduce dependency injection with Dagger
- [ ] Test it under performance constraints
- [ ] Introduce a backup mechanism. Maybe a new API? Should the app abandon offline mode only?

<img src="./docs/all_dialysis_screen.png" alt="All dialysis screen. It just lists basic information about the dialysis inserted, like Initial UF and Final UF" width="200"/>

<img src="./docs/add_dialysis_screen.png" alt="Add dialysis screen, it allows the user to insert the Initial UF, Final UF and some observation" width="200"/>

<img src="./docs/generate_pdf_screen.png" alt="PDF Generator screen. It allows the user to mention a patient name and the target interval of time" width="200"/>

<img src="./docs/select_date_pdf_generator.png" alt="PDF Generator screen. Showing a Date Picker" width="200"/>

<img src="./docs/all_pdfs_screen.png" alt="All PDFs screen. It just lists all the PDFs currently generated" width="200"/>
