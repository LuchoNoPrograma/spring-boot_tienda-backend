/*
* Calling code for RESTCountries API:
*
* */
export type RestCountryType = {
  name: {
    common: string
  },
  idd: {
    root: string
    suffixes?: string[]
  },
  flag: string,
  flags: {
    png: string
    svg: string
  }
}