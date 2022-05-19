export interface Task {
  id: number,
  name: string,
  nbr: number,
  duration: number,
  marginTotal: number,
  marginFree: number,
  startAsap: Date,
  startLatest: Date,
  endAsap: Date,
  endLatest: Date
}
