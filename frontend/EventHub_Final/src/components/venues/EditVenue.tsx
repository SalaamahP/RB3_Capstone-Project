import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import apiService, {Venue} from '../../services/api'
import CreateVenue from './CreateVenue';

export default function EditVenue() {
  const { id } = useParams<{ id: string }>();
  const [venue, setVenue] = useState<Venue | null>(null);

  useEffect(() => {
    if (id) {
      apiService.getVenue(Number(id))
      .then(setVenue)
      .catch(err => console.error('Failed to load venue: ', err) );
    }
  }, [id]);
  if (!venue) return <div>Loading..</div>;

  return <CreateVenue venue={venue} />

}