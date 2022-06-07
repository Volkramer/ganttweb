export interface Task {
  id: number,
  name: string,
  nbr: number,
  duration: number,
  marginTotal: number,
  marginFree: number,
  startAsap: number,
  startLatest: number,
  endAsap: number,
  endLatest: number
}
